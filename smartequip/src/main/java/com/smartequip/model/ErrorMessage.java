package com.smartequip.model;

/**
 * ErrorMessage response 
 * @author Shraban.Rana
 *
 */
public class ErrorMessage {
	private int statusCode;
	private String status;
	private String timeStamp;
	private String message;
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 
	 */
	public ErrorMessage() {
		super();
	}
	/**
	 * @param statusCode
	 * @param status
	 * @param timeStamp
	 * @param message
	 */
	public ErrorMessage(int statusCode, String status, String timeStamp, String message) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.timeStamp = timeStamp;
		this.message = message;
	}

	

}