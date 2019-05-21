package com.jackpon.po;

public class Business {
	private	String address;
	private Integer id;
	private String name;
	private String password;
	private String password_salt;
	private	String phone;
	private	Double turnover;
	private String img_path;
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public String getAddress() {
		return address;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public String getPassword_salt() {
		return password_salt;
	}
	public String getPhone() {
		return phone;
	}
	public Double getTurnover() {
		return turnover;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPassword_salt(String password_salt) {
		this.password_salt = password_salt;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}
}
