package com.adeies.adeies.enterprise.dto;

import java.util.Date;

public record EmployeeDto(Long employeeId,
                          String firstname,
                          String lastname,
                          Date birthdate,
                          String maritalStatus,
                          String department,
                          String area,
                          String position){

}
