package com.uet.esxi_api.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.uet.esxi_api.entity.WebUser;
import com.uet.esxi_api.service.UserService;

@Configuration
public class DatabaseResource {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public void initDatabase() {
		if (userService.findByUsername("20021455") == null) {
			WebUser user = new WebUser(null, "20021455", passwordEncoder.encode("123456789"), new ArrayList<>());
			userService.save(user);
		}
	}
}	
