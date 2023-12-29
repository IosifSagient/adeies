package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.DaysOff.InitializeDaysOffRq;
import com.adeies.adeies.enterprise.entities.DaysOff;
import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import com.adeies.adeies.enterprise.repository.DaysOffRepo;
import com.adeies.adeies.enterprise.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DaysOffServiceImpl implements DaysOffService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private DaysOffDefinitionRepo daysOffDefinitionRepo;
    @Autowired
    private DaysOffRepo daysOffRepo;

    @Override
    public DaysOff initializeDaysOff(InitializeDaysOffRq initializeDaysOffRq) {

        User requestFrom = userRepo.findById(initializeDaysOffRq.getUserId())
                .orElseThrow(() -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(), ErrorCode.USER_NOT_FOUND.toString()));
        DaysOffDefinition dayOffType = daysOffDefinitionRepo.findById(initializeDaysOffRq.getDefinitionId())
                .orElseThrow(() -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(), ErrorCode.DAY_OFF_TYPE_NOT_FOUND.toString()));

        DaysOff initializedDayOff = new DaysOff();
        initializedDayOff.setUser(requestFrom);
        initializedDayOff.setDefinition(dayOffType);
        initializedDayOff.setTotal(initializeDaysOffRq.getTotal());
        initializedDayOff.setAvailable(initializeDaysOffRq.getAvailable());


        return daysOffRepo.save(initializedDayOff);
    }


}
