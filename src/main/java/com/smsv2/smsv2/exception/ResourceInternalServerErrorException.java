package com.smsv2.smsv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceInternalServerErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;

    public ResourceInternalServerErrorException(String msg) {
        super(String.format("'%s' Already Exist", msg));
        this.msg = msg;
    }
    
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
