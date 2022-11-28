package com.uet.esxi_api.exception.vm;

public class FailToSuspendVMException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FailToSuspendVMException(String message) {
		super(message);
	}
}
