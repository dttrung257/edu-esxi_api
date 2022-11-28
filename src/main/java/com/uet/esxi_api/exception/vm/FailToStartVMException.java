package com.uet.esxi_api.exception.vm;

public class FailToStartVMException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FailToStartVMException(String message) {
		super(message);
	}
}
