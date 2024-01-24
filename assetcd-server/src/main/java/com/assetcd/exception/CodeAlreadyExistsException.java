package com.assetcd.exception;

public class CodeAlreadyExistsException extends Exception {
	
	public CodeAlreadyExistsException() { 
	}

	public CodeAlreadyExistsException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
