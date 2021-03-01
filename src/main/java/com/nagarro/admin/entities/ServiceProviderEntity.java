package com.nagarro.admin.entities;

public class ServiceProviderEntity {
	String serviceProviderId;
	String serviceProviderName;
	String mobileNumber;
	String serviceId;
	String availablity;
	
	public ServiceProviderEntity () {
		
	}
	public ServiceProviderEntity(String serviceProviderId, String serviceProviderName, String mobileNumber,
			String serviceId, String availablity) {
		super();
		this.serviceProviderId = serviceProviderId;
		this.serviceProviderName = serviceProviderName;
		this.mobileNumber = mobileNumber;
		this.serviceId = serviceId;
		this.availablity = availablity;
	}
	public String getServiceProviderId() {
		return serviceProviderId;
	}
	public void setServiceProviderId(String serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	public String getServiceProviderName() {
		return serviceProviderName;
	}
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getAvailablity() {
		return availablity;
	}
	public void setAvailablity(String availablity) {
		this.availablity = availablity;
	}

	

}
