package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import org.springframework.stereotype.Service;

@Service
public interface DaysOffDefinitionService {
    DaysOffDefinition createDayOffCategory(DaysOffDefinition daysOffDefinition);
}
