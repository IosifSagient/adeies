package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.employee.EmployeeDto;
import com.adeies.adeies.enterprise.dto.employee.EmployeeRs;
import com.adeies.adeies.enterprise.dto.employee.searchEmployee.SearchEmployeeRq;
import com.adeies.adeies.enterprise.dto.employee.searchEmployee.SearchEmployeeRs;
import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.entities.WsStatus;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.mappers.UpdateEmployeeMapper;
import com.adeies.adeies.enterprise.repository.EmployeeRepo;
import com.adeies.adeies.enterprise.utils.EmployeeSpecifications;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UpdateEmployeeMapper updateEmployeeMapper = UpdateEmployeeMapper.INSTANCE;
    @Autowired
    private final EmployeeRepo employeeRepo;

    @Override
    public EmployeeRs createEmployee(EmployeeCard employee) {
        EmployeeRs response = new EmployeeRs();

        employeeRepo.save(employee);
        response.setEmployee(employee);
        response.setStatusCode(ErrorCode.SUCCESS.getValue());
        response.setErrorCode(ErrorCode.SUCCESS);
        return response;
    }

    @Override
    public WsStatus updateEmployee(EmployeeCard dto) {
        WsStatus response = new WsStatus();
        EmployeeCard currentEmp = employeeRepo.findById(
                        dto.getId())
                .orElseThrow(() -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(), ErrorCode.USER_NOT_FOUND.toString()));
            EmployeeCard newData = updateEmployeeMapper.toEntity(dto, currentEmp);
            employeeRepo.save(newData);
            response.setStatusCode(ErrorCode.SUCCESS.getValue());
            response.setErrorCode(ErrorCode.SUCCESS);
            return response;
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
        Specification<EmployeeCard> spec = Specification.where(null);

        if (request.getDepartment() != null && !request.getDepartment().isEmpty() && request.getArea() != null && !request.getArea().isEmpty()) {
            spec = spec.and(EmployeeSpecifications.hasAreaAndDepartment(request.getArea(), request.getDepartment()));
        }
        if (request.getDepartment() != null && !request.getDepartment().isEmpty()) {
            spec = spec.and((EmployeeSpecifications.hasDepartment(request.getDepartment())));
        }
        if (request.getPosition() != null && !request.getPosition().isEmpty()) {
            spec = spec.and(EmployeeSpecifications.hasPosition(request.getPosition()));
        }
        if (request.getArea() != null && !request.getArea().isEmpty()) {
            spec = spec.and(EmployeeSpecifications.hasArea(request.getArea()));
        }

        response.setWsStatus(new WsStatus(ErrorCode.SUCCESS.getValue(), ErrorCode.SUCCESS));
        response.setEmployeeList(employeeRepo.findAll(spec));

        return response;

    }
}
