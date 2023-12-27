package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.employee.EmployeeDto;
import com.adeies.adeies.enterprise.dto.employee.EmployeeRs;
import com.adeies.adeies.enterprise.dto.employee.searchEmployee.SearchEmployeeRq;
import com.adeies.adeies.enterprise.dto.employee.searchEmployee.SearchEmployeeRs;
import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.entities.WsStatus;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    EmployeeRs createEmployee(EmployeeCard employee);
    WsStatus updateEmployee(EmployeeCard dto);

    WsStatus deleteEmployee(EmployeeDto employeeDto);

    SearchEmployeeRs searchEmployees(SearchEmployeeRq request);
}
