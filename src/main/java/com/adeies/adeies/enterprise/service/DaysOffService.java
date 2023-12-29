package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.DaysOff.InitializeDaysOffRq;
import com.adeies.adeies.enterprise.entities.DaysOff;
import org.springframework.stereotype.Service;

@Service
public interface DaysOffService {
    DaysOff initializeDaysOff(InitializeDaysOffRq initializeDaysOffRq);
}
