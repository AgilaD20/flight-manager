package com.example.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.Exception.EntityAlreadyPresentException;
import com.example.demo.Exception.EntityNotPresentException;
import com.example.demo.Exception.FlightNotFoundException;
import com.example.demo.ui.ErrorResponse;

@RestControllerAdvice
public class FlightAppControllerAdvice {
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotPresentException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value(),System.currentTimeMillis()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleEntityAlreadyPresentException(EntityAlreadyPresentException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(),HttpStatus.CONFLICT.value(),System.currentTimeMillis()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleFlightNotFoundException(FlightNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value(),System.currentTimeMillis()));
	}
	
	
	

}
