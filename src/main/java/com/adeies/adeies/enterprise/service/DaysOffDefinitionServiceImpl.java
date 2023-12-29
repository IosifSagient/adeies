package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DaysOffDefinitionServiceImpl implements DaysOffDefinitionService {
    @Autowired
    private DaysOffDefinitionRepo daysOffDefinitionRepo;

    @Override
    public DaysOffDefinition createDayOffCategory(DaysOffDefinition daysOffDefinition) {
        return daysOffDefinitionRepo.save(daysOffDefinition);
    }
}

