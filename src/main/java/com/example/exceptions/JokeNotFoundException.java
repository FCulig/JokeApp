package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JokeNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 5479691069841334493L;

	public JokeNotFoundException(Long id) {
		super("Could not find joke with id="+id);
	}
	
	public JokeNotFoundException(String message) {
		super(message);
	}
}
