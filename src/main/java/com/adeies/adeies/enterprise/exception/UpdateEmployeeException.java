package com.adeies.adeies.enterprise.exception;

import com.adeies.adeies.enterprise.enums.ErrorCode;

public class UpdateEmployeeException extends ValidationFaultException {
    private String errorCode;
    private String ErrorDescription;

    public UpdateEmployeeException(String firstname) {
        super(ErrorCode.ERROR_UPDATING_EMPLOYEE.getValue(),
              "Error on updating user : " + firstname);
    }
}
