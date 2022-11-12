package com.uet.esxi_api.exception.vm;

public class VMAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VMAlreadyExistsException(String message) {
		super(message);
	}
}
