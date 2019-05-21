package com.jackpon.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jackpon.po.Customer;
import com.jackpon.po.Order;
import com.jackpon.service.CustomerService;
import com.jackpon.util.SessionKeyOpenId;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SessionKeyOpenId sessionKeyOpenId;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@ResponseBody
	@RequestMapping(value = "/loginOrNot")
	public Integer loginOrNot(@RequestParam(value = "skey",required = false) String skey) {
		if (redisTemplate.hasKey(skey)) {
			return 1;
		}
		return 0;
	}
	//登录
	@ResponseBody
	@RequestMapping(value = "/customerLogin")
	public Map<String,Object> customerLogin(
            @RequestParam(value = "code",required = false) String code,
            @RequestParam(value = "rawData",required = false) String rawData,
            @RequestParam(value = "signature",required = false) String signature,
            @RequestParam(value = "encrypteData",required = false) String encrypteData,
            @RequestParam(value = "iv",required = false) String iv){

		Map<String,Object> map = new HashMap<String, Object>(  );
		 //用户非敏感信息
	    JSONObject rawDataJson = JSON.parseObject( rawData );
	    //从微信服务器获取SessionKey、OpenId
	    JSONObject SessionKeyOpenId = sessionKeyOpenId.getSessionKeyOrOpenId( code );
	    String openid = SessionKeyOpenId.getString("openid" );
	    String sessionKey = SessionKeyOpenId.getString( "session_key" );
	    
	    Customer customer = customerService.findByOpenid( openid );
	    //uuid生成唯一key
	    String skey = UUID.randomUUID().toString();
	    if(customer==null){
	        //入库
	        String nickName = rawDataJson.getString( "nickName" );
	        String avatarUrl = rawDataJson.getString( "avatarUrl" );
	        String gender  = rawDataJson.getString( "gender" );
	        String city = rawDataJson.getString( "city" );
	        String country = rawDataJson.getString( "country" );
	        String province = rawDataJson.getString( "province" );
	        String phone = rawDataJson.getString( "phoneNumber" );
	        phone="13729212463";
	    
	        customer = new Customer();
	        customer.setOpenid(openid);
	        customer.setCreateTime( new Date() );
	        customer.setSessionKey(sessionKey);
	        customer.setBalance(0);
	        customer.setSkey( skey );
	        customer.setPhone(phone);
	        customer.setAddress(country+" "+province+" "+city);
	        customer.setAvatarUrl(avatarUrl);
	        customer.setGender(gender);
	        customer.setNickName(nickName);
	        
	        customerService.insert( customer );
	    }else {//skey设置十天过期，每十天更新下微信信息
	        //已存在
	        //log.info( "用户openid已存在,不需要插入" );
	    	 customer.setSkey( skey );
	    	 customerService.updateCustomerSkey(customer);
	    }
	    //根据openid查询skey是否存在
	    String skey_redis = (String) redisTemplate.opsForValue().get( openid );
	    if(StringUtils.isNotBlank( skey_redis )){
	        //存在 删除 skey 重新生成skey 将skey返回
	        redisTemplate.delete( skey_redis );
	 
	    }
        //  缓存一份新的
        JSONObject sessionObj = new JSONObject(  );
        sessionObj.put( "openId",openid );
        sessionObj.put( "sessionKey",sessionKey );
        redisTemplate.opsForValue().set( skey,sessionObj.toJSONString() ,20,TimeUnit.MINUTES);
        redisTemplate.opsForValue().set( openid,skey ,20,TimeUnit.MINUTES);
 
        //把新的sessionKey和oppenid返回给小程序
        map.put( "skey",skey );
	    map.put( "result","1" );
	 
	    JSONObject userInfo = sessionKeyOpenId.getUserInfo( encrypteData, sessionKey, iv );
	    System.out.println("根据解密算法获取的userInfo="+userInfo);
	    userInfo.put( "balance",customer.getBalance() );
	    map.put( "userInfo",userInfo );
	return map;
	}
//	@RequestMapping(value = "/customerLogin")
//	public @ResponseBody Integer customerLogin(@RequestBody Customer customer) {
//		//返回用户id,前端记得把id存到cookie
//		return customerService.customerLogin(customer);
//	}
	//注册
	@RequestMapping(value = "/customerRegister")
	public @ResponseBody Integer customerRegister(@RequestBody Customer customer) {
		return customerService.customerRegister(customer);
	}
	//获取用户信息
	@RequestMapping(value = "/getCustomer")
	public @ResponseBody Customer getCustomer(@RequestBody Customer customer) {
		return customerService.getCustomer(customer);
	}
	//修改用户信息
	@RequestMapping(value = "/updateCustomer")
	public @ResponseBody Integer updateCustomer(@RequestBody Customer customer) {
		return customerService.updateCustomer(customer);
	}
	//修改用户密码
	@RequestMapping(value = "/updateCustomerPassword")
	public @ResponseBody Integer updateCustomerPassword(@RequestBody Customer customer) {
		return customerService.updateCustomerPassword(customer);
	}
	//获取用户订单信息
	@RequestMapping(value = "/listOrders")
	public @ResponseBody List<Order> listOrders(@RequestBody Customer customer) {
		return customerService.listOrders(customer);
	}
	//下订单
	@RequestMapping(value = "/placeOrder")
	public @ResponseBody Integer placeOrder(@RequestBody Order order) {
		order.setOrder_time(new Date());
		return customerService.placeOrder(order);
	}
	//取消订单
	@RequestMapping(value = "/cancelOrder")
	public @ResponseBody Integer cancelOrder(@RequestBody Order order) {
		return customerService.cancelOrder(order);
	}
	//确认收货
	@RequestMapping(value = "/confirmReceipt")
	public @ResponseBody Integer confirmReceipt(@RequestBody Order order) {
		return customerService.confirmReceipt(order);
	}
}
