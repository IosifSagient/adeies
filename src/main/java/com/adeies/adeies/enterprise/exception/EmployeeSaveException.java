package com.adeies.adeies.enterprise.exception;

import com.adeies.adeies.enterprise.enums.ErrorCode;

public class EmployeeSaveException extends ValidationFaultException{

    public EmployeeSaveException(Long employeeId){
        super(ErrorCode.WRONG_EMPLOYEE_DATA.getValue(), "Can not save Employee: " + employeeId + "due to Wrong Type of Data inserted");
    }

}
