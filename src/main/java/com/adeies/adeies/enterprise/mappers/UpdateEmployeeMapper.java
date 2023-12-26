package com.adeies.adeies.enterprise.mappers;

import com.adeies.adeies.enterprise.dto.EmployeeDto;
import com.adeies.adeies.enterprise.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Component
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateEmployeeMapper {

    UpdateEmployeeMapper INSTANCE = Mappers.getMapper(UpdateEmployeeMapper.class);

    @Mapping(target = "employeeId", ignore = true)
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "employeeId", ignore = true)
    Employee toEntity(EmployeeDto dto, @MappingTarget Employee currentEmployee);
}
