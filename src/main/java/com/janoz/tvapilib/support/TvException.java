package com.janoz.tvapilib.support;

public class TvException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public TvException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TvException(String message) {
		super(message);
	}
	
	

}
