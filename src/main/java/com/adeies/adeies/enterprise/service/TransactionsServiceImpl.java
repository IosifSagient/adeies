package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.config.JwtService;
import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.enums.Status;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import com.adeies.adeies.enterprise.repository.TransactionsRepo;
import com.adeies.adeies.enterprise.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {

    private final UserRepo userRepo;
    private final DaysOffDefinitionRepo daysOffDefinitionRepo;
    private final JwtService jwtService;
    private final TransactionsRepo trxRepo;

    @Override
    public void requestDaysOff(RequestDaysOffRq dayOffRq) {

        User user = userRepo.findById(dayOffRq.getUserId()).orElseThrow(
                () -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),
                                                   ErrorCode.USER_NOT_FOUND.toString()));

        DaysOffDefinition dayOffType = daysOffDefinitionRepo
                .findById(dayOffRq.getDaysOffDefinitionId()).orElseThrow(
                        () -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),
                                                           ErrorCode.DAY_OFF_TYPE_NOT_FOUND.toString()));

        Transactions transaction = new Transactions();
        transaction.setUser(user);
        transaction.setApprovedBy(null);
        transaction.setDefinition(dayOffType);
        transaction.setStartDate(dayOffRq.getStartDate());
        transaction.setEndDate(dayOffRq.getEndDate());
        transaction.setStatus(Status.PENDING);
        transaction.setDays(getDayDifference(dayOffRq.getStartDate(), dayOffRq.getEndDate()));
        transaction.setDescription(dayOffRq.getDescription());
        trxRepo.save(transaction);

    }


    public Long getDptIdFromJwt(String jwt) {
        jwt = jwt.substring(7);
        return Long.parseLong(jwtService.extractDepartmentId(jwt));
    }

    Integer getDayDifference(LocalDate startDate, LocalDate endDate) {
        final DayOfWeek startW = startDate.getDayOfWeek();
        final DayOfWeek endW = endDate.getDayOfWeek();
        final long days = ChronoUnit.DAYS.between(startDate, endDate);
        final long daysWithoutWeekends = days - 2 * ((days + startW.getValue()) / 7);
        return (int) (daysWithoutWeekends + (startW == DayOfWeek.SUNDAY ? 1 : 0) + ((endW == DayOfWeek.SUNDAY) ? 1 : 0));
    }
}
