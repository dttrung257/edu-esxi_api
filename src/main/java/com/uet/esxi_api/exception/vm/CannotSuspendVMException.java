package com.uet.esxi_api.exception.vm;

public class CannotSuspendVMException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CannotSuspendVMException(String message) {
		super(message);
	}
}
