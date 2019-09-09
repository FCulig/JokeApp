package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoReactionsFoundException extends RuntimeException{
	private static final long serialVersionUID = 7514904342631024325L;

	public NoReactionsFoundException(String message) {
		super(message);
	}
}
