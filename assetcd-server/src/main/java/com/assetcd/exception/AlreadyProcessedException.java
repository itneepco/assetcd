package com.assetcd.exception;

public class AlreadyProcessedException extends Exception {
	
	public AlreadyProcessedException() { 
	}

	public AlreadyProcessedException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
