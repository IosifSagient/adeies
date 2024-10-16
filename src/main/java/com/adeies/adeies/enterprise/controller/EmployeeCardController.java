package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.employee.EmployeeDto;
import com.adeies.adeies.enterprise.dto.employee.EmployeeRs;
import com.adeies.adeies.enterprise.dto.employee.searchEmployee.SearchEmployeeRq;
import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.enums.ResponseMessages;
import com.adeies.adeies.enterprise.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeCardController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/createEmployeeCard")
    public ResponseEntity<EmployeeRs> createEmployeeCard(@RequestBody EmployeeCard employee) {
        EmployeeRs newEmployeeRs = employeeService.createEmployee(employee);
        return ResponseEntity.ok(newEmployeeRs);

    }

    @PostMapping("/updateEmployeeCard")
    public ResponseEntity<SuccessResponse> updateEmployeeCard(@RequestBody EmployeeCard dto) {
        employeeService.updateEmployee(dto);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @PostMapping("/deleteEmployeeCard")
    public ResponseEntity<SuccessResponse> deleteEmployeeCard(@RequestBody EmployeeDto dto) {
        employeeService.deleteEmployee(dto);
        return ResponseEntity.ok(new SuccessResponse(ResponseMessages.SUCCESSFUL_DELETE, null));
    }

    @PostMapping("/searchEmployeeCards")
    public ResponseEntity<List<EmployeeCard>> searchEmployeeCard(
            @RequestBody SearchEmployeeRq request) {
        List<EmployeeCard> res = employeeService.searchEmployees(request);
        return ResponseEntity.ok(res);
    }

}
