package com.adeies.adeies.enterprise.enums;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR("500"),
    NOT_FOUND("400"),
    SUCCESS("0000"),
    WRONG_EMPLOYEE_DATA("9991"),
    USER_NOT_FOUND("9923"),
    ERROR_UPDATING_EMPLOYEE ("9992") ;
    private final String errorCode;


    ErrorCode(String code){
        this.errorCode = code;

    }

    public String getValue() {
        return errorCode;
    }
}

