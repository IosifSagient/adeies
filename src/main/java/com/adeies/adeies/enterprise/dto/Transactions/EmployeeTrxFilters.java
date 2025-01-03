package com.adeies.adeies.enterprise.dto.Transactions;

import com.adeies.adeies.enterprise.enums.Status;

public record EmployeeTrxFilters (String startDate, String endDate, Long employeeId, Status status){}
