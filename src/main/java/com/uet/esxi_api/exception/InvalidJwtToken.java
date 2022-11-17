package com.uet.esxi_api.exception;

public class InvalidJwtToken extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidJwtToken(String message) {
		super(message);
	}
}
