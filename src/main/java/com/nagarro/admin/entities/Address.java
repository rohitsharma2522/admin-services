package com.nagarro.admin.entities;

public class Address {
	String userAddress;
	String state;
	String city;
	int pinCode;
	
	public Address() {
		super();
	}
	
	public Address(String userAddress, String state, String city, int pinCode) {
		super();
		this.userAddress = userAddress;
		this.state = state;
		this.city = city;
		this.pinCode = pinCode;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getPinCode() {
		return pinCode;
	}
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

}
