package com.adeies.adeies.enterprise.mappers;
import com.adeies.adeies.enterprise.dto.EmployeeDto;
import com.adeies.adeies.enterprise.model.Employee;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Component
@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface UpdateEmployeeMapper {

    UpdateEmployeeMapper INSTANCE = Mappers.getMapper(UpdateEmployeeMapper.class);

    @Mapping(target = "employeeId",ignore = true)
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "employeeId", ignore = true)
    Employee toEntity(EmployeeDto dto,  @MappingTarget Employee currentEmployee);
}
