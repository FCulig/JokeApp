package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FavoriteNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -8908483317733722633L;
	
	public FavoriteNotFoundException(String message) {
		super(message);
	}

}
