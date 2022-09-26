package com.smartequip.model;

import org.springframework.stereotype.Component;

@Component
public class SmartequipResponse {

	private String message;
	private String status;
	private int status_code;

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

	public int getStatus_code() {
		return status_code;
	}

	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}

	public SmartequipResponse(String message, String status, int status_code) {
		super();
		this.message = message;
		this.status = status;
		this.status_code = status_code;
	}

	public SmartequipResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}
