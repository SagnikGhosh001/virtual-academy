package com.smsv2.smsv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceInternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceInternalServerErrorException(String msg) {
        super(String.format("'%s' Already Exist", msg));
    }
}
