package com.adeies.adeies.enterprise.dto.employee.searchEmployee;

import com.adeies.adeies.enterprise.entities.EmployeeCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchEmployeeRs {
    private List<EmployeeCard> employeeList;
}
