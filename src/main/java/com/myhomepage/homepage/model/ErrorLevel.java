package com.myhomepage.homepage.model;

public enum ErrorLevel {
	WARNING("WARNING"), 
	DANGER("DANGER");
	
	private String value;
	
	ErrorLevel(String value) {
		this.value = value;
	}
	
    @Override
    public String toString() {
        return value;
    }
}
