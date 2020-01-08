package com.julio.backendmc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.julio.backendmc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e,HttpServletRequest request)
	{
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());//Se instancia el estandar error
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
