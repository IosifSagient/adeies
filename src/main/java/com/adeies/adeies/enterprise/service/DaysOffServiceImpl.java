package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.DaysOff.DayOffRq;
import com.adeies.adeies.enterprise.dto.DaysOff.InitializeDaysOffRq;
import com.adeies.adeies.enterprise.entities.DaysOff;
import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.enums.Status;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import com.adeies.adeies.enterprise.repository.DaysOffRepo;
import com.adeies.adeies.enterprise.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    public DaysOff initializeDaysOff(InitializeDaysOffRq initializeDaysOffRq) {

        User requestFrom = userRepo.findById(initializeDaysOffRq.getUserId()).orElseThrow(
                () -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),
                                                   ErrorCode.USER_NOT_FOUND.toString()));
        DaysOffDefinition dayOffType = daysOffDefinitionRepo.findById(
                initializeDaysOffRq.getDefinitionId()).orElseThrow(
                () -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),
                                                   ErrorCode.DAY_OFF_TYPE_NOT_FOUND.toString()));

        DaysOff initializedDayOff = new DaysOff();
        initializedDayOff.setUser(requestFrom);
        initializedDayOff.setDefinition(dayOffType);
        initializedDayOff.setTotal(initializeDaysOffRq.getTotal());
        initializedDayOff.setAvailable(initializeDaysOffRq.getAvailable());


        return daysOffRepo.save(initializedDayOff);
    }

    @Override
    public Integer submitDaysOff(DayOffRq dayOffRq) {

        User user = userRepo.findById(dayOffRq.getUserId()).orElseThrow(
                () -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),
                                                   ErrorCode.USER_NOT_FOUND.toString()));

        DaysOffDefinition dayOffType = daysOffDefinitionRepo.findById(
                dayOffRq.getDaysOffDefinitionId()).orElseThrow(
                () -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),
                                                   ErrorCode.DAY_OFF_TYPE_NOT_FOUND.toString()));


        Transactions transaction = new Transactions();
        transaction.setUser(user);
        transaction.setDescription(dayOffRq.getDescription());
        transaction.setStatus(Status.PENDING);
        transaction.setDefinition(dayOffType);
        transaction.setStartDate(dayOffRq.getStartDate());
        transaction.setEndDate(dayOffRq.getEndDate());
        transaction.setDays(getDayDifference(dayOffRq.getStartDate(), dayOffRq.getEndDate()));
        //transaction.setApprovedBy();

        return null;
    }

    Integer getDayDifference(LocalDate startDate, LocalDate endDate) {
        final DayOfWeek startW = startDate.getDayOfWeek();
        final DayOfWeek endW = endDate.getDayOfWeek();
        final long days = ChronoUnit.DAYS.between(startDate, endDate);
        final long daysWithoutWeekends = days - 2 * ((days + startW.getValue()) / 7);
        return (int) (daysWithoutWeekends + (startW == DayOfWeek.SUNDAY ? 1 : 0) + ((endW == DayOfWeek.SUNDAY) ? 1 : 0));
    }

}
