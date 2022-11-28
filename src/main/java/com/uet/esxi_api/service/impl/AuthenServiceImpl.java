package com.uet.esxi_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.uet.esxi_api.dto.AuthenRequest;
import com.uet.esxi_api.dto.AuthenResponse;
import com.uet.esxi_api.entity.WebUser;
import com.uet.esxi_api.security.jwt.JwtUtil;
import com.uet.esxi_api.service.AuthenService;

@Service
public class AuthenServiceImpl implements AuthenService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public AuthenResponse signIn(AuthenRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword().trim());
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		WebUser user = (WebUser) authentication.getPrincipal();
		String jwtToken = jwtUtil.generateJwtToken(request.getUsername().trim());
		return new AuthenResponse(user.getUsername(), jwtToken);
	}

}
