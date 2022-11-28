package com.uet.esxi_api.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.uet.esxi_api.exception.vm.CannotSuspendVMException;
import com.uet.esxi_api.exception.vm.CannotUpdateStorageException;
import com.uet.esxi_api.exception.vm.CannotUpdateVMException;
import com.uet.esxi_api.exception.vm.FailToCreateVMException;
import com.uet.esxi_api.exception.vm.FailToDeleteVMException;
import com.uet.esxi_api.exception.vm.FailToStartVMException;
import com.uet.esxi_api.exception.vm.FailToStopVMException;
import com.uet.esxi_api.exception.vm.FailToSuspendVMException;
import com.uet.esxi_api.exception.vm.FailToUpdateVMException;
import com.uet.esxi_api.exception.vm.InsufficientConfigurationParametersException;
import com.uet.esxi_api.exception.vm.InvalidOSNameException;
import com.uet.esxi_api.exception.vm.NotFoundVMException;
import com.uet.esxi_api.exception.vm.NotFoundVMStateException;
import com.uet.esxi_api.exception.vm.VMAlreadyExistsException;
import com.uet.esxi_api.exception.vm.VMAlreadyInStateException;

@ControllerAdvice
public class VMExceptionHandler {
	@ExceptionHandler(NotFoundVMException.class)
	public ResponseEntity<ErrorDetails> handleNotFoundVMException(NotFoundVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.value(), "Not found VM",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
	}

	@ExceptionHandler(NotFoundVMStateException.class)
	public ResponseEntity<ErrorDetails> handleNotFoundVMStateException(NotFoundVMStateException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.value(), "Not found VM state",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
	}
	
	@ExceptionHandler(VMAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> handleVMAlreadyExistsException(VMAlreadyExistsException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "VM already exists",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(VMAlreadyInStateException.class)
	public ResponseEntity<ErrorDetails> handleVMAlreadyInStateException(VMAlreadyInStateException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(),
				"VM already in this state", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(CannotSuspendVMException.class)
	public ResponseEntity<ErrorDetails> handleCannotSuspendVMException(CannotSuspendVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(),
				"Unable to go to suspend state while virtual machine is shutting down", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(CannotUpdateVMException.class)
	public ResponseEntity<ErrorDetails> handleCannotUpdateVMException(CannotUpdateVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(),
				"you can only update the VM when it's turned off", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(CannotUpdateStorageException.class)
	public ResponseEntity<ErrorDetails> handleCannotUpdateStorageException(CannotUpdateStorageException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(),
				"You can only increase the hard drive capacity of the virtual machine", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(InsufficientConfigurationParametersException.class)
	public ResponseEntity<ErrorDetails> handleInsufficientConfigurationParametersException(
			InsufficientConfigurationParametersException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(),
				"Configuration parameters are not enough", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}

	@ExceptionHandler(InvalidOSNameException.class)
	public ResponseEntity<ErrorDetails> handleInvalidOSNameException(InvalidOSNameException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.value(), "OS name is invalid",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	
	@ExceptionHandler(FailToCreateVMException.class) 
	public ResponseEntity<ErrorDetails> handleFailToCreateVMException(FailToCreateVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail to create VM",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
	}
	
	@ExceptionHandler(FailToDeleteVMException.class) 
	public ResponseEntity<ErrorDetails> handleFailToDeleteVMException(FailToDeleteVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail to delete VM",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
	}
	
	@ExceptionHandler(FailToUpdateVMException.class) 
	public ResponseEntity<ErrorDetails> handleFailToUpdateVMException(FailToUpdateVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail to update VM",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
	}
	
	@ExceptionHandler(FailToStartVMException.class) 
	public ResponseEntity<ErrorDetails> handleFailToStartVMException(FailToStartVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail to power on VM",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
	}
	
	@ExceptionHandler(FailToSuspendVMException.class) 
	public ResponseEntity<ErrorDetails> handleFailToSuspendVMException(FailToSuspendVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail to suspend VM",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
	}
	
	@ExceptionHandler(FailToStopVMException.class) 
	public ResponseEntity<ErrorDetails> handleFailToStopVMException(FailToStopVMException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail to power off VM",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
	}
}
