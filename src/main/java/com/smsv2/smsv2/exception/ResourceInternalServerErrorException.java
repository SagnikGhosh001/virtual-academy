package com.smsv2.smsv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceInternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String resourcename;
	private String fieldname;
	private Object fieldvalue;

    public ResourceInternalServerErrorException(String resourcename, String fieldname,Object fieldvalue) {
    	super(String.format("%s's %s: '%s' is already exist: ",resourcename,fieldname,fieldvalue));
    	this.resourcename=resourcename;
    	this.fieldname=fieldname;
    	this.fieldvalue=fieldvalue;
    }
}
