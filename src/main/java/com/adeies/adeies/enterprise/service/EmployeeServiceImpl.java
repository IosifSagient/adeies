package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.EmployeeDto;
import com.adeies.adeies.enterprise.entities.Employee;
import com.adeies.adeies.enterprise.entities.WsStatus;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.exception.EmployeeSaveException;
import com.adeies.adeies.enterprise.exception.UpdateEmployeeException;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.mappers.UpdateEmployeeMapper;
import com.adeies.adeies.enterprise.model.EmployeeRs;
import com.adeies.adeies.enterprise.model.searchEmployee.SearchEmployeeRq;
import com.adeies.adeies.enterprise.model.searchEmployee.SearchEmployeeRs;
import com.adeies.adeies.enterprise.repository.EmployeeRepo;
import com.adeies.adeies.enterprise.utils.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo ;
    private final UpdateEmployeeMapper updateEmployeeMapper= UpdateEmployeeMapper.INSTANCE;

    @Override
    public EmployeeRs createEmployee(Employee employee) {
        EmployeeRs response = new EmployeeRs();

        try{
            employeeRepo.save(employee);
            response.setEmployee(employee);
            response.setStatusCode(ErrorCode.SUCCESS.getValue());
            response.setErrorCode(ErrorCode.SUCCESS);
            return response;
        } catch (RuntimeException e){
            throw  new ValidationFaultException(
                    ErrorCode.WRONG_EMPLOYEE_DATA.getValue(),
                    new EmployeeSaveException(employee.getEmployeeId()).getMessage());
        }
    }

    @Override
    public WsStatus updateEmployee(EmployeeDto dto) {
        WsStatus response = new WsStatus();
         employeeRepo.findById(
                 dto.employeeId())
                         .orElseThrow(()-> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),ErrorCode.USER_NOT_FOUND.toString()));
        try{
               Employee newData = updateEmployeeMapper.toEntity(dto);
                employeeRepo.save(newData) ;
                response.setStatusCode(ErrorCode.SUCCESS.getValue());
                response.setErrorCode(ErrorCode.SUCCESS);
               return response;
           } catch (RuntimeException e){
               throw new ValidationFaultException(
                       ErrorCode.ERROR_UPDATING_EMPLOYEE.getValue(),
                       new UpdateEmployeeException(dto.firstname()).getMessage());
           }

    }

    @Override
    public WsStatus deleteEmployee(EmployeeDto dto) {
        WsStatus response = new WsStatus();
        employeeRepo.deleteById(dto.employeeId());
        response.setErrorCode(ErrorCode.SUCCESS);
        response.setStatusCode(ErrorCode.SUCCESS.getValue());
        return response;
    }

    @Override
    public SearchEmployeeRs searchEmployees(SearchEmployeeRq request) {
        SearchEmployeeRs response = new SearchEmployeeRs();
        Specification<Employee> spec = Specification.where(null);

        if (request.getDepartment() != null && !request.getDepartment().isEmpty() && request.getArea() != null && !request.getArea().isEmpty()) {
            spec = spec.and(EmployeeSpecifications.hasAreaAndDepartment(request.getArea(), request.getDepartment()));
        }
        if (request.getDepartment() != null && !request.getDepartment().isEmpty()) {
            spec = spec.and((EmployeeSpecifications.hasDepartment(request.getDepartment())));
        }
        if (request.getPosition() != null && !request.getPosition().isEmpty()) {
           spec= spec.and(EmployeeSpecifications.hasPosition(request.getPosition()));
        }
        if (request.getArea() != null && !request.getArea().isEmpty()) {
           spec = spec.and(EmployeeSpecifications.hasArea(request.getArea()));
        }

        response.setWsStatus(new WsStatus(ErrorCode.SUCCESS.getValue(), ErrorCode.SUCCESS));
        response.setEmployeeList(employeeRepo.findAll(spec));

        return response;

    }
}
