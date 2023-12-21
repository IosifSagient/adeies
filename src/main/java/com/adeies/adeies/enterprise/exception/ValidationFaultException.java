package com.adeies.adeies.enterprise.exception;

import java.io.Serializable;

public class ValidationFaultException extends RuntimeException implements Serializable {

    private final String errorCode;
    private final String errorDescription;

    public ValidationFaultException(String errorCode, String errorDescription){
        super(errorCode + ":" + errorDescription );
        this.errorCode = errorCode;
        this.errorDescription=errorDescription;
    }
}
