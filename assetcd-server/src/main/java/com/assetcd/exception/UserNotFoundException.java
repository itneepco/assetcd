package com.assetcd.exception;

public class UserNotFoundException extends Exception {
	
	public UserNotFoundException() { 
	}

	public UserNotFoundException(String string) {
		super(string);
	}
	
	private static final long serialVersionUID = 1L;

}
