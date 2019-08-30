package com.example.exceptions;

import com.example.entities.User;

public class FavoriteAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1906595758945641437L;

	public FavoriteAlreadyExistsException(User usr) {
		super("User "+ usr.getUsername() +" already favorited this joke.");
	}
}
