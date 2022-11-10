package com.uet.esxi_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uet.esxi_api.entity.WebUser;
import com.uet.esxi_api.repository.UserRepository;
import com.uet.esxi_api.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public WebUser save(WebUser user) {
		return userRepository.save(user);
	}

	@Override
	public WebUser findByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}
		
}
