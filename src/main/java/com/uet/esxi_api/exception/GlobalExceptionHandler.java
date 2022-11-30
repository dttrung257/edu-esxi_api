package com.uet.esxi_api.exception;

import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.uet.esxi_api.exception.user.IncorrectPasswordException;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleException(Exception e, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Exception",
				webRequest.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(),
				"Incorrect request format", e.getAllErrors().stream()
						.map(objectError -> objectError.getDefaultMessage()).collect(Collectors.toList()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorDetails> handleValidationException(ValidationException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(),
				"Incorrect request format", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.FORBIDDEN.value(), "Access is denied",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(UsernameNotFoundException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.value(),
				"Username or password is incorrect", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
	}

	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<ErrorDetails> handleIncorrectPasswordException(IncorrectPasswordException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(),
				"Username or password is incorrect", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(InvalidJwtToken.class)
	public ResponseEntity<ErrorDetails> handleInvalidJwtToken(InvalidJwtToken e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "Jwt token is invalid",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
}