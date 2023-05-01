package com.apibanco.exception;

public class TechnicalException extends RuntimeException {
	
	private static final long serialVersionUID = 2943921281038327694L;
	
	public TechnicalException(String message) {
		super(message);
	}
	
	public TechnicalException(Throwable cause) {
		super(cause);
	}
	
	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}
	
}

