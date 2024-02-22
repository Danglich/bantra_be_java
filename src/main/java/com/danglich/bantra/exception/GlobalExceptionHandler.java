package com.danglich.bantra.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
		
		ErrorResponse errorResponse = ErrorResponse
				.builder().message(e.getMessage())
						.timestemp(new Date())
						.status(HttpStatus.NOT_FOUND.value())
						.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	  public ResponseEntity<ErrorResponse> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
	    List<String> errors = new ArrayList<>();

	    ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

	    
	    ErrorResponse errorResponse = ErrorResponse.builder()
	    								.message(errors)
	    								.status(HttpStatus.BAD_REQUEST.value())
	    								.timestemp(new Date())
	    								.build();

	    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	  }

	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
		e.printStackTrace(System.out);
		
		ErrorResponse errorResponse = ErrorResponse
				.builder().message(e.getLocalizedMessage())
						.timestemp(new Date())
						.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
