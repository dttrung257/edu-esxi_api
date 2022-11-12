package com.uet.esxi_api.exception.vm;

public class VMAlreadyInStateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VMAlreadyInStateException(String message) {
		super(message);
	}
}
