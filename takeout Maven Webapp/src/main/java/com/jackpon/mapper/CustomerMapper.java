package com.jackpon.mapper;

import java.util.List;

import com.jackpon.po.Customer;
import com.jackpon.po.Order;

public interface CustomerMapper {
	Customer checkLogin(Customer customer);
	void customerRegister(Customer customer);
	Customer getCustomer(Customer customer);
	List<Order> listOrders(Customer customer);
	Integer updateCustomer(Customer customer);
	Integer updateCustomerPassword(Customer customer);
	Integer placeOrder(Order order) ;
	Integer cancelOrder(Order order);
	Integer confirmReceipt(Order order);
	Customer findByOpenid(String openid);
	Integer insert(Customer customer);
	Integer updateCustomerSkey(Customer customer);
}
