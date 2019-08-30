package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1580247757075460362L;

	public UserNotFoundException(long userId) {
		super("Could not find user with id="+userId);
	}
	
	public UserNotFoundException(String username) {
		super("Could not find user with username="+username);
	}
}
