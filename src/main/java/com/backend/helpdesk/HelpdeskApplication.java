package com.backend.helpdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class HelpdeskApplication {

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC+7"));
	}
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}


}
