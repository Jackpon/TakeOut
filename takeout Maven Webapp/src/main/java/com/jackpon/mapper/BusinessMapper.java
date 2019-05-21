package com.jackpon.mapper;

import java.util.List;

import com.jackpon.po.Business;
import com.jackpon.po.Good;
import com.jackpon.po.Order;

public interface BusinessMapper {
	Business checkLogin(Business business);
	void businessRegister(Business business);
	Business getBusiness(Business business);
	List<Order> listOrders(Business business);
	Integer updateBusiness(Business business);
	Integer updateBusinessPassword(Business business);
	Integer cancelOrder(Order order);
	Integer acceptOrder(Order order);
	Integer inputGood(Good good);
	List<Business> getAllBusiness();
	List<Good> getBusinessGoods(Business business);
}
