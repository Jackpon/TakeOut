package com.jackpon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jackpon.mapper.CustomerMapper;
import com.jackpon.po.Customer;
import com.jackpon.po.Order;
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerMapper customerMapper;
	@Override
	public Integer customerLogin(Customer customer) {
		Integer status=0;
//		Customer customer2=customerMapper.checkLogin(customer);
//		//System.out.println(user2.getPassword());
//		if (customer2.getPassword().equals(customer.getPassword())) {
//			status=customer2.getId();
//		}
		return status;
	}

	@Override
	public Integer customerRegister(Customer customer) {
		customerMapper.customerRegister(customer);
//		if (customer.getId()>0) {
//			return 1;
//		}
		return 0;
	}

	@Override
	public Customer getCustomer(Customer customer) {
		return customerMapper.getCustomer(customer);
	}

	@Override
	public List<Order> listOrders(Customer customer) {
		return customerMapper.listOrders(customer);
	}

	@Override
	public Integer updateCustomer(Customer customer) {
		return customerMapper.updateCustomer(customer);
	}

	@Override
	public Integer updateCustomerPassword(Customer customer) {
		return customerMapper.updateCustomerPassword(customer);
	}

	@Override
	public Integer placeOrder(Order order) {
		customerMapper.placeOrder(order);
		if (order.getId()>0) {
			return 1;
		}
		return 0;
	}

	@Override
	public Integer cancelOrder(Order order) {
		customerMapper.cancelOrder(order);
		if (order.getId()>0) {
			return 1;
		}
		return 0;
	}

	@Override
	public Integer confirmReceipt(Order order) {
		customerMapper.confirmReceipt(order);
		if (order.getId()>0) {
			return 1;
		}
		return 0;
	}

	@Override
	public Customer findByOpenid(String openid) {
		return customerMapper.findByOpenid(openid);
	}

	@Override
	public void insert(Customer customer) {
		customerMapper.insert(customer);
	}

	@Override
	public Integer updateCustomerSkey(Customer customer) {
		return customerMapper.updateCustomerSkey(customer);
	}

}
