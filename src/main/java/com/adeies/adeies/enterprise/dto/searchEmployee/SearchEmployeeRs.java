package com.adeies.adeies.enterprise.dto.searchEmployee;

import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.entities.WsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchEmployeeRs {
    private WsStatus wsStatus;
    private List<EmployeeCard> employeeList;
}
