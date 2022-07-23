package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.models.UserRole;
import com.spring.permissions.UserPermissions;

@SpringBootApplication
public class SpringSecurity2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurity2Application.class, args);
		
	}

}
