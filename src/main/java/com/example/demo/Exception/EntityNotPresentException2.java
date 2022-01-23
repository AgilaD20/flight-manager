package com.example.demo.Exception;


public class EntityNotPresentException2 extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public EntityNotPresentException2(String message) {
		this.message = message;
	}
	

}
