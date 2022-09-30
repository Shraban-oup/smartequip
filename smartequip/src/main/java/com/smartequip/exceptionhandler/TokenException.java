package com.smartequip.exceptionhandler;

/**
 * Handled runtimeException Validation
 * @author Shraban.Rana
 *
 */
public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TokenException(String msg) {
		super(msg);
	}
}