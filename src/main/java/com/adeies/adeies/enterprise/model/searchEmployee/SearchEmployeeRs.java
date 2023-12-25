package com.adeies.adeies.enterprise.model.searchEmployee;

import com.adeies.adeies.enterprise.entities.WsStatus;
import com.adeies.adeies.enterprise.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SearchEmployeeRs {

    private WsStatus wsStatus;
    private List<Employee> employeeList;
}
