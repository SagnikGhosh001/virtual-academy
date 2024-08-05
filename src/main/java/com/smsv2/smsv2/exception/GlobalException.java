package com.smsv2.smsv2.exception;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {

    @Autowired
    private CustomExceptionTranslator exceptionTranslator;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErrorDetails> resourceBadRequestHandling(ResourceBadRequestException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceInternalServerErrorException.class)
    public ResponseEntity<ErrorDetails> handleResourceInternalServerError(ResourceInternalServerErrorException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandling(Exception exception, WebRequest request) {
        RuntimeException translatedException = exceptionTranslator.translateExceptionIfPossible((RuntimeException) exception);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), translatedException.getMessage(), request.getDescription(false));
        HttpStatus status = (translatedException instanceof ResourceInternalServerErrorException) ? HttpStatus.CONFLICT : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(errorDetails, status);
    }
}
