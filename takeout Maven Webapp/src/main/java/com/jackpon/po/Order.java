package com.jackpon.po;

import java.util.Date;

public class Order {
	private String customer_id;
	private Integer goods_id;
	private Integer id;
	private Integer number;
	private Date order_time;
	private Integer state;
	private Double total_cost;
	public String getCustomer_id() {
		return customer_id;
	}
	public Integer getGoods_id() {
		return goods_id;
	}
	
	public Integer getId() {
		return id;
	}
	public Integer getNumber() {
		return number;
	}
	public Date getOrder_time() {
		return order_time;
	}
	public Integer getState() {
		return state;
	}
	public Double getTotal_cost() {
		return total_cost;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public void setTotal_cost(Double total_cost) {
		this.total_cost = total_cost;
	}
}
