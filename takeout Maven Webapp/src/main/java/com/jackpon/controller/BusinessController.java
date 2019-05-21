package com.jackpon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jackpon.po.Business;
import com.jackpon.po.Good;
import com.jackpon.po.Order;
import com.jackpon.service.BusinessService;

@Controller
@RequestMapping("/business")
public class BusinessController {
	@Autowired
	private BusinessService businessService;
	//登录
	@RequestMapping(value = "/businessLogin")
	public @ResponseBody Integer businessLogin(@RequestBody Business business) {
		//返回用户id,前端记得把id存到cookie
		return businessService.businessLogin(business);
	}
	//注册
	@RequestMapping(value = "/businessRegister")
	public @ResponseBody Integer businessRegister(@RequestBody Business business) {
		if (businessService.businessRegister(business)!=0) {
			return 1;
		}
		return 0;
	}
	//获取所有商家
	@RequestMapping(value ="/getAllBusiness")
	public @ResponseBody List<Business> getAllBusiness() {
		return businessService.getAllBusiness();
	}
	//获取用户信息
	@RequestMapping(value = "/getBusiness")
	public @ResponseBody Business getBusiness(@RequestBody Business business) {
		return businessService.getBusiness(business);
	}
	//修改用户信息
	@RequestMapping(value = "/updateBusiness")
	public @ResponseBody Integer updateBusiness(@RequestBody Business business) {
		return businessService.updateBusiness(business);
	}
	//修改用户密码
	@RequestMapping(value = "/updateBusinessPassword")
	public @ResponseBody Integer updateBusinessPassword(@RequestBody Business business) {
		return businessService.updateBusinessPassword(business);
	}
	//获取用户订单信息
	@RequestMapping(value = "/listOrders")
	public @ResponseBody List<Order> listOrders(@RequestBody Business business) {
		return businessService.listOrders(business);
	}
	//取消订单
	@RequestMapping(value = "/cancelOrder")
	public @ResponseBody Integer cancelOrder(@RequestBody Order order) {
		return businessService.cancelOrder(order);
	}
	//接订单
	@RequestMapping(value = "/acceptOrder")
	public @ResponseBody Integer acceptOrder(@RequestBody Order order) {
		return businessService.acceptOrder(order);
	}
	//录入商品
	@RequestMapping(value = "/inputGood")
	public @ResponseBody Integer inputGood(@RequestBody Good good) {
		return businessService.inputGood(good);
	}
	//获取goods
	@RequestMapping(value = "/getBusinessGoods")
	public @ResponseBody List<Good> getBusinessGoods(@RequestBody Business business) {
		return businessService.getBusinessGoods(business);
	}
}
