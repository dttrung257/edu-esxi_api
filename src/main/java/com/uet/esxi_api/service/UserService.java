package com.uet.esxi_api.service;

import com.uet.esxi_api.entity.WebUser;

public interface UserService {
	WebUser save(WebUser user);
	WebUser findByUsername(String username);
}
