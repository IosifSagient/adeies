package com.adeies.adeies.enterprise.exception;

import lombok.Data;

@Data
public class ErrorsPattern {
    private String errorCode;
    private String errorDesc;

    public ErrorsPattern(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

}
