package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadJokeBodyException extends RuntimeException{
	private static final long serialVersionUID = -8314661662111788768L;
	
	public BadJokeBodyException(String message) {
		super(message);
	}

}
