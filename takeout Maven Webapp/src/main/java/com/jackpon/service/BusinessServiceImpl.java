package com.jackpon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jackpon.mapper.BusinessMapper;
import com.jackpon.po.Business;
import com.jackpon.po.Good;
import com.jackpon.po.Order;
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {
	@Autowired
	private BusinessMapper businessMapper;
	@Override
	public Integer businessLogin(Business business) {
		Integer status=0;
		Business business2=businessMapper.checkLogin(business);
		//System.out.println(user2.getPassword());
		if (business2.getPassword().equals(business.getPassword())) {
			status=business2.getId();
		}
		return status;
	}
	@Override
	public Integer businessRegister(Business business) {
		businessMapper.businessRegister(business);
		return business.getId();
	}
	@Override
	public Business getBusiness(Business business) {
		return businessMapper.getBusiness(business);
	}
	@Override
	public List<Order> listOrders(Business business) {
		return businessMapper.listOrders(business);
	}
	@Override
	public Integer updateBusiness(Business business) {
		return businessMapper.updateBusiness(business);
	}
	@Override
	public Integer updateBusinessPassword(Business business) {
		return businessMapper.updateBusinessPassword(business);
	}
	@Override
	public Integer cancelOrder(Order order) {
		businessMapper.cancelOrder(order);
		if (order.getId()>0) {
			return 1;
		}
		return 0;
	}
	@Override
	public Integer acceptOrder(Order order) {
		businessMapper.acceptOrder(order);
		if (order.getId()>0) {
			return 1;
		}
		return 0;
	}
	@Override
	public Integer inputGood(Good good) {
		businessMapper.inputGood(good);
		if (good.getId()>0) {
			return 1;
		}
		return 0;
	}
	@Override
	public List<Business> getAllBusiness() {
		return businessMapper.getAllBusiness();
	}
	@Override
	public List<Good> getBusinessGoods(Business business) {
		return businessMapper.getBusinessGoods(business);
	}
}
