package com.adeies.adeies.enterprise.controller;


import com.adeies.adeies.enterprise.dto.EmployeeDto;
import com.adeies.adeies.enterprise.entities.Employee;
import com.adeies.adeies.enterprise.entities.WsStatus;
import com.adeies.adeies.enterprise.model.EmployeeRs;
import com.adeies.adeies.enterprise.model.searchEmployee.SearchEmployeeRq;
import com.adeies.adeies.enterprise.model.searchEmployee.SearchEmployeeRs;
import com.adeies.adeies.enterprise.repository.EmployeeRepo;
import com.adeies.adeies.enterprise.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @PostMapping("/createEmployee")
    public ResponseEntity<EmployeeRs> createEmployee(@RequestBody Employee employee) {
        EmployeeRs newEmployeeRs = employeeService.createEmployee(employee);
        return new ResponseEntity<>(newEmployeeRs, HttpStatus.OK);

    }

    @PostMapping("/updateEmployee")
    public ResponseEntity<WsStatus> updateEmployee(@RequestBody EmployeeDto dto) {
        WsStatus resp = employeeService.updateEmployee(dto);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/deleteEmployee")
    public ResponseEntity<WsStatus> deleteEmployee(@RequestBody EmployeeDto dto) {
        WsStatus resp = employeeService.deleteEmployee(dto);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/searchEmployee")
    public ResponseEntity<SearchEmployeeRs> searchEmployee(@RequestBody SearchEmployeeRq request) {
        SearchEmployeeRs response = employeeService.searchEmployees(request);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
