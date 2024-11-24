package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import com.adeies.adeies.enterprise.dto.daysOff.UpdateRequestRq;
import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.enums.Status;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import com.adeies.adeies.enterprise.repository.EmployeeRepo;
import com.adeies.adeies.enterprise.repository.TransactionsRepo;
import com.adeies.adeies.enterprise.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.adeies.adeies.enterprise.utils.UserUtils;

@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {

    private final DaysOffDefinitionRepo daysOffDefinitionRepo;
    private final TransactionsRepo trxRepo;
    private final UserRepo userRepo;
    private final UserUtils userUtils;
    private final EmployeeRepo employeeRepo;
    @Override
    public void requestDaysOff(RequestDaysOffRq dayOffRq, OAuth2User oAuth2User) {
        DaysOffDefinition dayOffType = daysOffDefinitionRepo.findById(
                dayOffRq.getDaysOffDefinitionId()).orElseThrow(
                () -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),
                        ErrorCode.DAY_OFF_TYPE_NOT_FOUND.toString()));
        User user = userUtils.getUserFromOAuth(oAuth2User);


        if (isOverlappingRequest(user.getId(), dayOffRq.getStartDate(), dayOffRq.getEndDate())) {
            throw new ValidationFaultException("123432","OVERLAPPING DATES ");
        }

        Transactions transaction = buildTransaction(user, dayOffType, dayOffRq);
        trxRepo.save(transaction);
    }


    @Override
    public void updateDayOffRequest(UpdateRequestRq rq, OAuth2User oAuth2User) {

        DaysOffDefinition dayOffType = daysOffDefinitionRepo.findById(
                rq.getRequestDaysOffRq().getDaysOffDefinitionId()).orElseThrow(
                () -> new ValidationFaultException(ErrorCode.USER_NOT_FOUND.getValue(),
                        ErrorCode.DAY_OFF_TYPE_NOT_FOUND.toString()));
        User user = userRepo.findByEmail(oAuth2User.getAttribute("email")).orElseThrow(
                () -> new ValidationFaultException("1234", "User not found"));


        if (isOverlappingRequestForUpdateRq(user.getId(), rq.getRequestDaysOffRq().getStartDate(), rq.getRequestDaysOffRq().getEndDate(), rq.getTrxId())) {
            throw new ValidationFaultException("123432","OVERLAPPING DATES ");
        }

         Transactions transaction = buildTransaction(user, dayOffType, rq.getRequestDaysOffRq());
         transaction.setId(rq.getTrxId());

         trxRepo.save(transaction);
    }
    @Override
    public Page<Transactions> getUsersReq(Long id, Pageable pageable) {

        return trxRepo.getTrxByUser(id,pageable);
    }

    @Override
    public List<Transactions> getTrxByDepartment(User user, Pageable pageable) {
        Long deptId = user.getEmployeeCard().getDepartment().getId();
        List<Long> employeeList = employeeRepo.findAllInDpt(deptId);
        List<Long> users = userRepo.findUserFromEmployee(employeeList);
        return trxRepo.getTrxGivingListOfUserIds(users);
    }

    //    Integer getDayDifference(LocalDate startDate, LocalDate endDate) {
//        final DayOfWeek startW = startDate.getDayOfWeek();
//        final DayOfWeek endW = endDate.getDayOfWeek();
//        final long days = ChronoUnit.DAYS.between(startDate, endDate);
//        final long daysWithoutWeekends = days - 2 * ((days + startW.getValue()) / 7);
//        return (int) (daysWithoutWeekends + (startW == DayOfWeek.SUNDAY ? 1 : 0) + ((endW == DayOfWeek.SUNDAY) ? 1 : 0));
//    }
    public static int getDayDifference(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        int workdays = 0;
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            if (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY && currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workdays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return workdays;
    }
    private boolean isOverlappingRequest(Long userId, LocalDate startDate, LocalDate endDate) {
        Page<Transactions> pageOfTrx = trxRepo.getTrxByUser(userId, Pageable.unpaged());
        List<Transactions> existingTrxList = pageOfTrx.getContent();

        return existingTrxList.stream()
                .anyMatch(existingTrx ->
                        !startDate.isAfter(existingTrx.getEndDate()) && !endDate.isBefore(existingTrx.getStartDate())
                );
    }

    private boolean isOverlappingRequestForUpdateRq(Long userId, LocalDate startDate, LocalDate endDate, Long trxId) {
        Page<Transactions> pageOfTrx = trxRepo.getTrxByUser(userId, Pageable.unpaged());
        List<Transactions> existingTrxList = pageOfTrx.getContent();


        return existingTrxList.stream()
                .filter(trx -> !Objects.equals(trx.getId(), trxId))
                .anyMatch(existingTrx ->
                        !startDate.isAfter(existingTrx.getEndDate()) && !endDate.isBefore(existingTrx.getStartDate())
                );
    }
    private Transactions buildTransaction(User user, DaysOffDefinition dayOffType, RequestDaysOffRq dayOffRq) {
        Transactions transaction = new Transactions();
        transaction.setUser(user);
        transaction.setApprovedBy(null);  // Assuming this will be updated later
        transaction.setDefinition(dayOffType);
        transaction.setStartDate(dayOffRq.getStartDate());
        transaction.setEndDate(dayOffRq.getEndDate());
        transaction.setStatus(Status.PENDING);
        transaction.setDays(getDayDifference(dayOffRq.getStartDate(), dayOffRq.getEndDate()));
        transaction.setComment(dayOffRq.getComment());
        return transaction;
    }

}
