package com.mc.myapp.exceptions;

public class UserAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    public UserAlreadyExistException() {
        super("User is already in Exist!");
    }
}
