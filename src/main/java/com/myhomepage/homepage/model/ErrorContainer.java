package com.myhomepage.homepage.model;

public class ErrorContainer {
	private int code;
	private String type;
	private String message;
	private ErrorLevel level;
	
	public ErrorContainer(int code, String type, String message, ErrorLevel level) {
		this.code = code;
		this.type = type;
		this.message = message;
		this.level = level;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorLevel getLevel() {
		return level;
	}

	public void setLevel(ErrorLevel level) {
		this.level = level;
	}

}
