package com.ccc.api.controller;

public class ErrorController extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorController(){
        super("Book id not found");
    }
}
