package com.example.exceptions;

public class JokeNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5479691069841334493L;

	public JokeNotFoundException(Long id) {
		super("Could not find joke with id="+id);
	}
}
