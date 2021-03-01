package com.nagarro.admin.entities;

import com.nagarro.admin.entities.Address;

public class Users {
	String userId;
	String userName;
	Address address;
	
	public Users() {
		super();
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Users(String userId, String userName, Address address) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.address = address;
	}
	
}
