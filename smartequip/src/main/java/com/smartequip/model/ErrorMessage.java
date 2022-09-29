package com.smartequip.model;

/**
 * ErrorMessage response 
 * @author Shraban.Rana
 *
 */
public class ErrorMessage {
	private int statusCode;
	private String status;
	private String localDateTime;
	private String message;

	public ErrorMessage() {
		super();
	}

	public ErrorMessage(int statusCode, String status, String localDateTime, String message) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.localDateTime = localDateTime;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(String localDateTime) {
		this.localDateTime = localDateTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}