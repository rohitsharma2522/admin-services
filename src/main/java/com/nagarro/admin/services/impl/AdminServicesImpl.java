package com.nagarro.admin.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.nagarro.admin.constants.BookingStatus;
import com.nagarro.admin.entities.BookService;
import com.nagarro.admin.entities.ProviderDetailsWithBooking;
import com.nagarro.admin.entities.UserService;
import com.nagarro.admin.services.AdminServices;

@Service
public class AdminServicesImpl implements AdminServices {

	Map<Integer, List<UserService>> serviceLocationMap = new HashMap<Integer, List<UserService>>();

	private Map<String, BookService> bookingRequests = new ConcurrentHashMap<String, BookService>();

	private Map<String, ProviderDetailsWithBooking> providerDetailsWithBooking = new ConcurrentHashMap<String, ProviderDetailsWithBooking>();

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public List<UserService> getAllServices() {
		List<UserService> services = new ArrayList<>();
		services.add(new UserService("001", "Salon", "Haircut for men and women"));
		services.add(new UserService("002", "Cleaning", "Floor and house cleaning"));
		services.add(new UserService("003", "Electrician", "Doorstep repair in 90mins"));
		services.add(new UserService("004", "Carpenter", "For all types of wood work"));
		return services;
	}

	@Override
	public UserService getServiceById(String serviceId) {
		Optional<UserService> result = getAllServices().stream().filter(p -> serviceId.equals(p.getServiceId()))
				.findFirst();
		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public BookService bookService(BookService bookingDetails) {
		bookingDetails.setBoookingRefNum(UUID.randomUUID().toString());
		bookingDetails.setTimestamp(LocalDateTime.now());
		bookingDetails.setBookingStatus(BookingStatus.INPROCESS);
		this.bookingRequests.put(bookingDetails.getBoookingRefNum(), bookingDetails);
		System.out.println("bookingDetails are" + bookingDetails);
		jmsTemplate.convertAndSend("BookingRequest", bookingDetails);
		return bookingDetails;

	}

	@Override
	public List<UserService> getServiceByPincode(int pinCode) {
		List<UserService> ll = new ArrayList<>();
		List<UserService> ll1 = new ArrayList<>();
		List<UserService> ll2 = new ArrayList<>();
		ll.add(new UserService("001", "Salon", "Haircut for men and women"));
		ll.add(new UserService("002", "Cleaning", "Floor and house cleaning"));
		ll1.add(new UserService("003", "Electrician", "Doorstep repair in 90mins"));
		ll1.add(new UserService("004", "Carpenter", "For all types of wood work"));
		if (pinCode == 160062) {
			serviceLocationMap.put(160062, ll);
			return ll;
		} else if (pinCode == 160061) {
			serviceLocationMap.put(160061, ll1);
			return ll1;
		} else {
			serviceLocationMap.put(160071, ll);
			return ll;
		}
	}

	@Override
	public BookService getBookingDetailsById(String bookingRefNum) {
		System.out.print("bookingRequests" + this.bookingRequests.get(bookingRefNum));
		return this.bookingRequests.get(bookingRefNum);
	}

	@Override
	public ProviderDetailsWithBooking getProviderDetailsWithBooking(String bookingRefNum) {
		return this.providerDetailsWithBooking.get(bookingRefNum);
	}

	@Override
	@JmsListener(destination = "ServiceProviderAvailable")
	public void bookDetailsRecievedToProvider(String bookingRefNum) {
		System.out.println("ServiceProviderAvailable for bookingRefNum " + bookingRefNum);
		BookService bookingDetails = this.bookingRequests.get(bookingRefNum);
		bookingDetails.setBookingStatus(BookingStatus.RECIEVED);
		this.bookingRequests.put(bookingDetails.getBoookingRefNum(), bookingDetails);
		jmsTemplate.convertAndSend("AcceptOrReject", bookingDetails);

	}

	@Override
	@JmsListener(destination = "ServiceProviderNotAvailable")
	public void providerNotAvailable(String bookingRefNum) {
		System.out.println("ServiceProviderNotAvailable for bookingRefNum" + bookingRefNum);
		BookService bookingDetails = this.bookingRequests.get(bookingRefNum);
		bookingDetails.setBookingStatus(BookingStatus.PENDING);
		this.bookingRequests.put(bookingDetails.getBoookingRefNum(), bookingDetails);
	}

	@Override
	@JmsListener(destination = "BookingConfirmed")
	public void bookingConfirmed(ProviderDetailsWithBooking providerDetails) {
		System.out.println("BookingConfirmed with provider details" + providerDetails);
		BookService bookingDetails = this.bookingRequests.get(providerDetails.getBookingRefNum());
		bookingDetails.setBookingStatus(BookingStatus.CONFIRMED);
		this.bookingRequests.put(bookingDetails.getBoookingRefNum(), bookingDetails);
		this.providerDetailsWithBooking.put(providerDetails.getBookingRefNum(), providerDetails);
		jmsTemplate.convertAndSend("SendProviderDetailsToUser", providerDetails);
	}
}
