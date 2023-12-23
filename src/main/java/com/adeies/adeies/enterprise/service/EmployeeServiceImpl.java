package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.UpdateEmployeeDto;
import com.adeies.adeies.enterprise.entities.Employee;
import com.adeies.adeies.enterprise.entities.WsStatus;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.exception.EmployeeSaveException;
import com.adeies.adeies.enterprise.exception.UpdateEmployeeException;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.mappers.UpdateEmployeeMapper;
import com.adeies.adeies.enterprise.model.employee.EmployeeRs;
import com.adeies.adeies.enterprise.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
            throw  new ValidationFaultException(ErrorCode.WRONG_EMPLOYEE_DATA.getValue(), new EmployeeSaveException(employee.getEmployeeId()).getMessage());
        }
    }

    @Override
    public WsStatus updateEmployee(UpdateEmployeeDto dto) {
        WsStatus response = new WsStatus();
        Optional<Employee> currentEmployee = employeeRepo.findById(dto.employeeId());
        Optional.of(currentEmployee).get().ifPresentOrElse((oldEmployee)->{
           try{ Employee newData = updateEmployeeMapper.toEntity(dto);
            employeeRepo.save(newData) ;
            response.setStatusCode(ErrorCode.SUCCESS.getValue());
            response.setErrorCode(ErrorCode.SUCCESS);} catch (RuntimeException e){
               throw new ValidationFaultException(ErrorCode.ERROR_UPDATING_EMPLOYEE.getValue(),new UpdateEmployeeException(dto.firstname()).getMessage());

           }
        },()-> {
            throw new RuntimeException(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        });

        return response;
    }
}
