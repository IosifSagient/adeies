package com.adeies.adeies.enterprise.model.searchEmployee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchEmployeeRq {
    private String department;
    private String area;
    private String position;

}
