package com.uet.esxi_api.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.uet.esxi_api.exception.user.IncorrectPasswordException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(AccessDeniedException.class)
	ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.FORBIDDEN.value(), "Access is denied", e.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized", e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.value(), "Username or password is incorrect", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
	}
	
	@ExceptionHandler(IncorrectPasswordException.class)
	ResponseEntity<Object> handleIncorrectPasswordException(IncorrectPasswordException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "Username or password is incorrect", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
}
