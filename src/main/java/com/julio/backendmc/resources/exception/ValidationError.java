package com.julio.backendmc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	// Get y set de list
	public List<FieldMessage> getErrors() {
		return errors;
	}

	// Adicionar un error que va tener un nombre y mensaje
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));// adicionare a la lista un nombre y el mensaje de la lista
	}
}
