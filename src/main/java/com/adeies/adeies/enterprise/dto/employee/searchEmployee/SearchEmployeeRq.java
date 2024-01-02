package com.adeies.adeies.enterprise.dto.employee.searchEmployee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchEmployeeRq {
    private Long department;
    private String area;
    private String position;
}
