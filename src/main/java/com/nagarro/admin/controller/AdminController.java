package com.nagarro.admin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nagarro.admin.entities.BookService;
import com.nagarro.admin.entities.ProviderDetailsWithBooking;
import com.nagarro.admin.entities.UserService;
import com.nagarro.admin.services.AdminServices;
import com.netflix.discovery.EurekaClient;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private EurekaClient eurekaClient;

	@Resource(name = "restTemp")
	private RestTemplate restTemplate;

	@Value("${server.port}")
	private int port;

	@Resource
	AdminServices adminService;

	public EurekaClient getEurekaClient() {
		return eurekaClient;
	}

	public void setEurekaClient(EurekaClient eurekaClient) {
		this.eurekaClient = eurekaClient;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping(value = "/getallservices")
	public List<UserService> getAllServices() {
		return adminService.getAllServices();
	}

	@GetMapping(value = "/service/{serviceid}")
	UserService getServiceById(@PathVariable(name = "serviceid") String serviceId) {
		return adminService.getServiceById(serviceId);
	}

	@GetMapping(value = "/services/{pincode}")
	List<UserService> getServiceByPincode(@PathVariable(name = "pincode") int pinCode) {
		return adminService.getServiceByPincode(pinCode);
	}

	@PostMapping(value = "/bookservice")
	public BookService bookService(@RequestBody BookService bookingDetails) {
		return adminService.bookService(bookingDetails);
	}

	@GetMapping(value = "/bookingdetails/{bookingrefnum}")
	public BookService getBookingDetailsById(@PathVariable(name = "bookingrefnum") String bookingRefnum) {
		return adminService.getBookingDetailsById(bookingRefnum);
	}
	
	@GetMapping(value = "/providerdetails/{bookingrefnum}")
	public ProviderDetailsWithBooking getProviderDetailsByBookingRefNum(@PathVariable(name = "bookingrefnum") String bookingRefNum) {
		return adminService.getProviderDetailsWithBooking(bookingRefNum);
	}

}
