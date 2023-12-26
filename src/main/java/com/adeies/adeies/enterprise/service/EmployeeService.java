package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.EmployeeDto;
import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.entities.WsStatus;
import com.adeies.adeies.enterprise.dto.EmployeeRs;
import com.adeies.adeies.enterprise.dto.searchEmployee.SearchEmployeeRq;
import com.adeies.adeies.enterprise.dto.searchEmployee.SearchEmployeeRs;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    EmployeeRs createEmployee(EmployeeCard employee);
    WsStatus updateEmployee(EmployeeCard dto);

    WsStatus deleteEmployee(EmployeeDto employeeDto);

    SearchEmployeeRs searchEmployees(SearchEmployeeRq request);
}
