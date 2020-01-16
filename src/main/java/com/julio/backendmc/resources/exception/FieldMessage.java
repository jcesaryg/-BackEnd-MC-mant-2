package com.julio.backendmc.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fieldaName;
	private String message;
	
	//Constructores
	public FieldMessage() {
	}

	public FieldMessage(String fieldaName, String message) {
		super();
		this.fieldaName = fieldaName;
		this.message = message;
	}
	
	//generar Get y Set
	public String getFieldaName() {
		return fieldaName;
	}

	public void setFieldaName(String fieldaName) {
		this.fieldaName = fieldaName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

	
}
