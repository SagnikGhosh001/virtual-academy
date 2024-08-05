package com.smsv2.smsv2.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;


import java.sql.SQLException;

@Component
public class CustomExceptionTranslator {

//    public RuntimeException translateExceptionIfPossible(RuntimeException ex) {
//        if (ex instanceof DataIntegrityViolationException) {
//            if (ex.getCause() instanceof ConstraintViolationException) {
//                ConstraintViolationException cve = (ConstraintViolationException) ex.getCause();
//                if ("23000".equals(cve.getSQLState())) {
//                    return new ResourceInternalServerErrorException(cve.getConstraintName());
//                }
//            }
//        }
//        return ex;
//    }
}
