package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.daysOff.InitializeDaysOffRq;
import com.adeies.adeies.enterprise.entities.DaysOff;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import com.adeies.adeies.enterprise.repository.DaysOffRepo;
import com.adeies.adeies.enterprise.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DaysOffServiceImpl implements DaysOffService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final DaysOffDefinitionRepo daysOffDefinitionRepo;
    @Autowired
    private final DaysOffRepo daysOffRepo;


    @Override
    public void initializeDaysOff(InitializeDaysOffRq initializeDaysOffRq) {
        User user = userRepo.findById(initializeDaysOffRq.getUserId()).orElseThrow(
                () -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),
                                                   ErrorCode.USER_NOT_FOUND.toString()));

        ArrayList<DaysOff> daysOffList = new ArrayList<>();
        initializeDaysOffRq.getDaysOff().forEach(dayOff -> {
            daysOffDefinitionRepo.findById(dayOff.getDefinition().getId()).orElseThrow(
                    () -> new ValidationFaultException(ErrorCode.DAY_OFF_TYPE_NOT_FOUND.getValue(),
                                                       ErrorCode.DAY_OFF_TYPE_NOT_FOUND.toString()));
            dayOff.setUser(user);
            daysOffList.add(dayOff);
        });

        daysOffRepo.saveAll(daysOffList);
    }

}
