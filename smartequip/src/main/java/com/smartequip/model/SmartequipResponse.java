package com.smartequip.model;

/**
 * Success message response class
 * 
 * @author Shraban.Rana
 *
 */
public class SmartequipResponse {

	private String message;
	private String status;
	private int statusCode;

	public SmartequipResponse() {
		super();
	}

	public SmartequipResponse(String message, String status, int statusCode) {
		super();
		this.message = message;
		this.status = status;
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
