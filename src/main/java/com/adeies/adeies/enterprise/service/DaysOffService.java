package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.daysOff.InitializeDaysOffRq;
import org.springframework.stereotype.Service;

@Service
public interface DaysOffService {
    void initializeDaysOff(InitializeDaysOffRq initializeDaysOffRq);
}
