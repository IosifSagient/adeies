package com.adeies.adeies.enterprise.mappers;
import com.adeies.adeies.enterprise.dto.EmployeeDto;
import com.adeies.adeies.enterprise.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UpdateEmployeeMapper {

    UpdateEmployeeMapper INSTANCE = Mappers.getMapper(UpdateEmployeeMapper.class);

    @Mapping(target = "employeeId",ignore = true)
    EmployeeDto toDto(Employee employee);

    Employee toEntity(EmployeeDto dto);
}
