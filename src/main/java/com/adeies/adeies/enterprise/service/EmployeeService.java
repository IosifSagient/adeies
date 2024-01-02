package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.employee.EmployeeDto;
import com.adeies.adeies.enterprise.dto.employee.EmployeeRs;
import com.adeies.adeies.enterprise.dto.employee.searchEmployee.SearchEmployeeRq;
import com.adeies.adeies.enterprise.entities.EmployeeCard;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    EmployeeRs createEmployee(EmployeeCard employee);

    EmployeeRs updateEmployee(EmployeeCard dto);

    void deleteEmployee(EmployeeDto employeeDto);

    List<EmployeeCard> searchEmployees(SearchEmployeeRq request);
}
