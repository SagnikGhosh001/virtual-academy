package com.smsv2.smsv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;

    public ResourceBadRequestException(String msg) {
        super(String.format("Bad request for : '%s'", msg));
        this.msg = msg;
    }
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
