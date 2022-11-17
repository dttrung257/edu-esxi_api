package com.uet.esxi_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uet.esxi_api.dto.AuthenRequest;

@RestController
@RequestMapping("/api")
public class AuthenController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/authen/sign_in")
	ResponseEntity<Object> signIn(@RequestBody AuthenRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return ResponseEntity.status(HttpStatus.OK).body("Login success");
	}
	
	@GetMapping("/authen/log_out")
	ResponseEntity<Object> logOut() {
		SecurityContextHolder.getContext().setAuthentication(null);
		return ResponseEntity.ok("Logout success");
	}
}
