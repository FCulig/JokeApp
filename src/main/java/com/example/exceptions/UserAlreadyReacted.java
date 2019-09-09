package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserAlreadyReacted extends RuntimeException{
	
	private static final long serialVersionUID = 1557604509892852456L;
	
	public UserAlreadyReacted(String message) {
		super(message);
	}
}
