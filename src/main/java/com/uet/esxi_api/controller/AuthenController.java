package com.uet.esxi_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uet.esxi_api.dto.AuthenRequest;
import com.uet.esxi_api.dto.AuthenResponse;
import com.uet.esxi_api.service.AuthenService;

@RestController
@RequestMapping("/api")
public class AuthenController {
	@Autowired
	private AuthenService authenService;
	
	@PostMapping("/authen/sign_in")
	ResponseEntity<AuthenResponse> signIn(@RequestBody AuthenRequest request) {
		return ResponseEntity.ok(authenService.signIn(request));
	}
}
