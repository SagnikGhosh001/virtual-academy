package com.smsv2.smsv2.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalException {
	//handler method to handling specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?>resourceNotFoundHandling(ResourceNotFoundException exception,WebRequest request)
	{
		ErrorDetails errorDetails=
				new ErrorDetails(new Date(),exception.getMessage(),request.getDescription(false));
	return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	// Handler method for ResourceBadRequestException
    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<?> resourceBadRequestHandling(ResourceBadRequestException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // General handler method for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
