package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongDateFormatException extends RuntimeException{
	private static final long serialVersionUID = -6535740973584538542L;

	public WrongDateFormatException(String message) {
		super(message);
	}
}
