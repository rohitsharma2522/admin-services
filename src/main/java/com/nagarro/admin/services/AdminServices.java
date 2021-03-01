package com.nagarro.admin.services;

import java.util.List;

import com.nagarro.admin.entities.BookService;
import com.nagarro.admin.entities.ProviderDetailsWithBooking;
import com.nagarro.admin.entities.UserService;

public interface AdminServices {
	List<UserService> getAllServices();

	UserService getServiceById(String serviceId);
	
	List<UserService> getServiceByPincode(int pinCode);
	
	public BookService bookService(BookService bookingDetails);
	
	public BookService getBookingDetailsById (String bookingRefNum);
	
	public void bookDetailsRecievedToProvider(String bookingRefNum);

	public void providerNotAvailable(String bookingRefNum);
	
	void bookingConfirmed(ProviderDetailsWithBooking providerDetails);
	
	ProviderDetailsWithBooking getProviderDetailsWithBooking(String bookingRefNum);
	

}
