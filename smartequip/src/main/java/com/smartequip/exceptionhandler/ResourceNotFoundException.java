package com.smartequip.exceptionhandler;

/**
 * Handled runtimeException ResourceNotFound
 * @author Shraban.Rana
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}