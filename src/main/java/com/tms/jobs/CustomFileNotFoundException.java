package com.tms.jobs;

public class CustomFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomFileNotFoundException(String message) {
		super(message);
	}
	
	public CustomFileNotFoundException(String message, Throwable cause) {
		super(message,cause);
	}
}
