package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadUserBodyException extends RuntimeException{
	private static final long serialVersionUID = -6509950559857022487L;

	public BadUserBodyException(String message) {
		super(message);
	}
}
