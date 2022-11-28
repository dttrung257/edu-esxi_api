package com.uet.esxi_api.exception.vm;

public class FailToStopVMException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FailToStopVMException(String message) {
		super(message);
	}
}
