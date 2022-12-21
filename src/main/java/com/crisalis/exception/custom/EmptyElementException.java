package com.crisalis.exception.custom;

public class EmptyElementException extends RuntimeException{

	private static final String DESCRIPTION = "Elemento vacio (400)";
	
	public EmptyElementException(String detail) {
		super(DESCRIPTION + ". " + detail);
		
		
	}
}
