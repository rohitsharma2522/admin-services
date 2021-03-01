package com.nagarro.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nagarro.admin.entities.BookService;
import com.nagarro.admin.entities.ProviderDetailsWithBooking;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableJms
public class AdminApplication {

	@Autowired
	  private ObjectMapper objectMapper;
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Bean(name = "restTemp")
//	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	 @PostConstruct
	  public void setUp() {
	    objectMapper.registerModule(new JavaTimeModule());
	  }
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(objectMapper);
		Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
		typeIdMappings.put("JMS_TYPE", BookService.class);
		typeIdMappings.put("JMS_TYPE1", ProviderDetailsWithBooking.class);
		converter.setTypeIdMappings(typeIdMappings);
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("JMS_TYPE");
		converter.setTypeIdPropertyName("JMS_TYPE1");

		return converter;
	}

}
