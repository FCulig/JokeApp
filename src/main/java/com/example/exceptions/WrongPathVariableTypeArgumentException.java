package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongPathVariableTypeArgumentException extends RuntimeException{
	private static final long serialVersionUID = -643523075972436117L;

	public WrongPathVariableTypeArgumentException(String message) {
		super(message);
	}
}
