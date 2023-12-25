package com.adeies.adeies.enterprise.model;

import com.adeies.adeies.enterprise.model.Employee;
import com.adeies.adeies.enterprise.entities.WsStatus;
import lombok.Data;

@Data
public class EmployeeRs extends WsStatus {

   private Employee employee;
}