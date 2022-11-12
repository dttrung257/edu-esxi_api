package com.uet.esxi_api.exception;

import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.uet.esxi_api.exception.user.IncorrectPasswordException;
import com.uet.esxi_api.exception.vm.CannotSuspendVMException;
import com.uet.esxi_api.exception.vm.CannotUpdateStorageException;
import com.uet.esxi_api.exception.vm.CannotUpdateVMException;
import com.uet.esxi_api.exception.vm.InsufficientConfigurationParametersException;
import com.uet.esxi_api.exception.vm.InvalidOSNameException;
import com.uet.esxi_api.exception.vm.NotFoundVMException;
import com.uet.esxi_api.exception.vm.VMAlreadyExistsException;
import com.uet.esxi_api.exception.vm.VMAlreadyInStateException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "Incorrect request format",
				e.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.toList()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler(ValidationException.class)
	ResponseEntity<Object> handleValidationException(ValidationException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "Incorrect request format",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.FORBIDDEN.value(), "Access is denied",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
	}

	@ExceptionHandler(AuthenticationException.class)
	ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.value(),
				"Username or password is incorrect", e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
	}

	@ExceptionHandler(IncorrectPasswordException.class)
	ResponseEntity<Object> handleIncorrectPasswordException(IncorrectPasswordException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(),
				"Username or password is incorrect", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(NotFoundVMException.class)
	ResponseEntity<Object> handleNotFoundVMException(NotFoundVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.value(), "Not found VM",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
	}
	
	@ExceptionHandler(VMAlreadyExistsException.class)
	ResponseEntity<Object> handleVMAlreadyExistsException(VMAlreadyExistsException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "VM already exists",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler(VMAlreadyInStateException.class)
	ResponseEntity<Object> handleVMAlreadyInStateException(VMAlreadyInStateException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "VM already in this state",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler(CannotSuspendVMException.class)
	ResponseEntity<Object> handleCannotSuspendVMException(CannotSuspendVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "Unable to go to suspend state while virtual machine is shutting down",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler(CannotUpdateVMException.class)
	ResponseEntity<Object> handleCannotUpdateVMException(CannotUpdateVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "you can only update the VM when it's turned off",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler(CannotUpdateStorageException.class)
	ResponseEntity<Object> handleCannotUpdateStorageException(CannotUpdateStorageException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "You can only increase the hard drive capacity of the virtual machine",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler(InsufficientConfigurationParametersException.class)
	ResponseEntity<Object> handleInsufficientConfigurationParametersException(InsufficientConfigurationParametersException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "Configuration parameters are not enough",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler(InvalidOSNameException.class)
	ResponseEntity<Object> handleInvalidOSNameException(InvalidOSNameException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "OS name is invalid",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
}
