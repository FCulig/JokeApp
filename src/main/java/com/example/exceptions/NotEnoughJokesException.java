package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughJokesException extends RuntimeException{
	private static final long serialVersionUID = 7612767743107260715L;

	public NotEnoughJokesException(String message) {
		super(message);
	}
}
