package com.jackpon.po;

public class Good {
	private Integer business_id;
	private Integer id;
	private String img;
	private Integer limit_num;
	private String name;
	private Double price;
	public Integer getBusiness_id() {
		return business_id;
	}
	public Integer getId() {
		return id;
	}
	public String getImg() {
		return img;
	}
	public Integer getLimit_num() {
		return limit_num;
	}
	public String getName() {
		return name;
	}
	public Double getPrice() {
		return price;
	}
	public void setBusiness_id(Integer business_id) {
		this.business_id = business_id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public void setLimit_num(Integer limit_num) {
		this.limit_num = limit_num;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
