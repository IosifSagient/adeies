package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.entities.Employee;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.exception.EmployeeSaveException;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.model.employee.EmployeeRs;
import com.adeies.adeies.enterprise.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo ;

    @Override
    public EmployeeRs createEmployee(Employee employee) {
        EmployeeRs response = new EmployeeRs();

        try{
            employeeRepo.save(employee);
            response.setEmployee(employee);
            response.setStatusCode(ErrorCode.SUCCESS.getValue());
            response.setErrorCode(ErrorCode.SUCCESS);
        } catch (RuntimeException e){
            throw  new ValidationFaultException(ErrorCode.WRONG_EMPLOYEE_DATA.getValue(), new EmployeeSaveException(employee.getEmployeeId()).getMessage());
        }
        return response;
    }
}
