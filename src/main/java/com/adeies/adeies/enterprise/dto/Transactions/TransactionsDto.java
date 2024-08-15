package com.adeies.adeies.enterprise.dto.Transactions;

import com.adeies.adeies.enterprise.enums.Status;

import java.time.LocalDate;

public record TransactionsDto(Long id, Long userId , String approvedBy, String definition, LocalDate startDate, LocalDate endDate , Status status, Integer days , String comment) {
}
