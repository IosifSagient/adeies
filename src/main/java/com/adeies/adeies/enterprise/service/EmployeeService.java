package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.EmployeeDto;
import com.adeies.adeies.enterprise.entities.Employee;
import com.adeies.adeies.enterprise.entities.WsStatus;
import com.adeies.adeies.enterprise.model.employee.EmployeeRs;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    EmployeeRs createEmployee(Employee employee);
    WsStatus updateEmployee(EmployeeDto dto);

    WsStatus deleteEmployee(EmployeeDto employeeDto);

}
