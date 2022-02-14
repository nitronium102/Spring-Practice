package com.ex2.jwttoken.exception;

public class NoSuchAdminException extends RuntimeException{
	public NoSuchAdminException(String message){
		super(message);
	}
}
