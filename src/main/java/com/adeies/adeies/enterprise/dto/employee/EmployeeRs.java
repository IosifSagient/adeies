package com.adeies.adeies.enterprise.dto.employee;

import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.entities.WsStatus;
import lombok.Data;

@Data
public class EmployeeRs extends WsStatus {
   private EmployeeCard employee;
}