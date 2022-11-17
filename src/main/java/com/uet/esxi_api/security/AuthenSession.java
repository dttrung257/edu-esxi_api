package com.uet.esxi_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.boot.web.servlet.server.Session.Cookie;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class AuthenSession {
	@Autowired
	private HttpSecurity httpSecurity;
	
	public Cookie getCookie() {
		return httpSecurity.getSharedObject(Session.class).getCookie();
	}
}
