package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import org.springframework.stereotype.Service;

@Service
public interface TransactionsService {
    void requestDaysOff(RequestDaysOffRq rq);
}
