package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import com.adeies.adeies.enterprise.entities.Transactions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionsService {
    void requestDaysOff(RequestDaysOffRq rq);

    List<Transactions> getDepartmentPendingTransactions(String jwt);
}
