package com.uet.esxi_api.exception.vm;

public class FailToDeleteVMException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FailToDeleteVMException(String message) {
		super(message);
	}
}
