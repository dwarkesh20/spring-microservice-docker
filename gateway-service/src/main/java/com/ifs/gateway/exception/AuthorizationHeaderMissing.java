package com.ifs.gateway.exception;

public class AuthorizationHeaderMissing extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AuthorizationHeaderMissing(String msg) {
		super(msg);
	}

}
