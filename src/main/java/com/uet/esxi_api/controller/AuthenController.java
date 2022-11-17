package com.uet.esxi_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uet.esxi_api.dto.AuthenRequest;
import com.uet.esxi_api.dto.AuthenResponse;
import com.uet.esxi_api.entity.WebUser;
import com.uet.esxi_api.security.jwt.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthenController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/authen/sign_in")
	ResponseEntity<Object> signIn(@RequestBody AuthenRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		WebUser user = (WebUser) authentication.getPrincipal();
		String jwtToken = jwtUtil.generateJwtToken(request.getUsername());
		return ResponseEntity.ok(new AuthenResponse(user.getUsername(), jwtToken));
	}
}
