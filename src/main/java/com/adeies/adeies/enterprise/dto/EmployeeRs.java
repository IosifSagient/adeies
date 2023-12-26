package com.adeies.adeies.enterprise.dto;

import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.entities.WsStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class EmployeeRs extends WsStatus {
   private EmployeeCard employee;
}