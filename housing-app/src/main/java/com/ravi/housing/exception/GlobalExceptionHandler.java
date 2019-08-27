package com.ravi.housing.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ValidationError> invalidArguments(MethodArgumentNotValidException exception) {
		ValidationError validationError = null;
		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
			validationError = new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return new ResponseEntity<ValidationError>(validationError, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler
	public ResponseEntity<ValidationError> InvalidInput(ConstraintViolationException exception) {
		return getResponse(exception.getLocalizedMessage());
	}

	@ExceptionHandler
	public ResponseEntity<ValidationError> invalidData(ApplicationException exception) {
		return getResponse(exception.getLocalizedMessage());
	}

	@ExceptionHandler
	public ResponseEntity<ValidationError> userNotFound(UserNotFoundException exception) {
		return getResponse(exception.getLocalizedMessage());
	}

	@ExceptionHandler
	public ResponseEntity<ValidationError> houseNotFound(HouseNotFoundException exception) {
		return getResponse(exception.getLocalizedMessage());
	}

	private ResponseEntity<ValidationError> getResponse(String message) {
		return new ResponseEntity<ValidationError>(new ValidationError(message), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler
	public ResponseEntity<ValidationError> InvalidInput(InvalidFormatException exception) {
		return getResponse("Please provide the date in correct format (yyyy-mm-dd)");
	}
	
	@ExceptionHandler
	public ResponseEntity<ValidationError> unKonownException(Exception exception) {
		return getResponse("There was an error proccesing the request. Please try again later");
	}

}
