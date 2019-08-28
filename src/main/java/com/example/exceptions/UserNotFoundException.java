package com.example.exceptions;

public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1580247757075460362L;

	public UserNotFoundException(long userId) {
		super("User does not exist id="+userId);
	}
}
