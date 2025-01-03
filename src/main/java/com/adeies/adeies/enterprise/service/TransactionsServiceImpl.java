package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.SearchFilters;
import com.adeies.adeies.enterprise.dto.Transactions.TransactionsDTO;
import com.adeies.adeies.enterprise.dto.Transactions.TrxStatusUpdate;
import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import com.adeies.adeies.enterprise.dto.daysOff.UpdateRequestRq;
import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.ErrorCode;
import com.adeies.adeies.enterprise.enums.Status;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.mappers.TrxDisplayMapper;
import com.adeies.adeies.enterprise.pojos.DaysOffAvailablePK;
import com.adeies.adeies.enterprise.repository.*;
import com.adeies.adeies.enterprise.utils.TransactionsSpecification;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.adeies.adeies.enterprise.utils.UserUtils;

@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {

    private final DaysOffDefinitionRepo daysOffDefinitionRepo;
    @Autowired
    private final DaysOffRepo daysOffRepo;
    private final TransactionsRepo trxRepo;
    private final UserRepo userRepo;
    private final UserUtils userUtils;
    private final EmployeeRepo employeeRepo;
    private final TrxDisplayMapper trxDisplayMapper;
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

        if(validateDaysOffRequest(dayOffRq.getStartDate(),dayOffRq.getEndDate(),user,dayOffRq.getDaysOffDefinitionId())){
            throw new ValidationFaultException("2342","not enough days ");
        }

        Transactions transaction = buildTransaction(user, dayOffType, dayOffRq);
        trxRepo.save(transaction);
    }
    @Override
    public void updateTrxStatus(TrxStatusUpdate trxStatusUpdate , User user){
        Transactions trx = trxRepo.findById(trxStatusUpdate.getTrxId()).orElseThrow(() -> new ValidationFaultException("4546","Could not update trx"));
        trx.setStatus(trxStatusUpdate.getStatus());
        trx.setApprovedBy(user);
        trxRepo.save(trx);
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

        if(validateDaysOffRequest(rq.getRequestDaysOffRq().getStartDate(),rq.getRequestDaysOffRq().getEndDate(),user,rq.getRequestDaysOffRq().getDaysOffDefinitionId())){
            throw new ValidationFaultException("2342","not enough days ");
        }

         Transactions transaction = buildTransaction(user, dayOffType, rq.getRequestDaysOffRq());
         transaction.setId(rq.getTrxId());

         trxRepo.save(transaction);
    }
    @Override
    public Page<Transactions> getUsersReq(Long id, Pageable pageable) {

        return trxRepo.getTrxByUser(id,pageable);
    }

    private List<Long> findAllUsersInDptFromManager(User user) {
        Long deptId = user.getEmployeeCard().getDepartment().getId();
        List<Long> employeeList = employeeRepo.findAllInDpt(deptId);
        return userRepo.findUserFromEmployee(employeeList);
    }

    public List<TransactionsDTO> getTrxByDepartment(User user, Pageable pageable, List<Status> statuses) {
        List<Long> userIds = findAllUsersInDptFromManager(user);
        if (userIds == null || userIds.isEmpty()) {
            throw new IllegalArgumentException("No users found in the manager's department.");
        }
        Specification<Transactions> spec = Specification
                .where(TransactionsSpecification.hasStatus(statuses))
                .and(TransactionsSpecification.hasUser(userIds));
        return trxRepo.findAll(spec, pageable)  // Apply pageable to limit results
                .stream()
                .map(trxDisplayMapper::toDto)
                .collect(Collectors.toList());
    }
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

    public Integer calculateDaysRequested(User user , Long definitionId) {

        List<Integer> filteredTransactions = trxRepo
                .getTrxByUser(user.getId(), Pageable.unpaged())
                .stream()
                .filter(transaction -> (isThisYearsTrxs(transaction) && isTransactionPendingOrApproved(transaction) && Objects.equals(transaction.getDefinition().getId(), definitionId)))
                .map(Transactions::getDays)
                .toList();

        return filteredTransactions.stream()
                .reduce(0, Integer::sum);

    }

    private boolean isTransactionPendingOrApproved(Transactions transactions) {
        return transactions.getStatus().equals(Status.PENDING) || transactions.getStatus().equals(Status.ACCEPTED);
    }

    private boolean isThisYearsTrxs(Transactions transactions){
        int nextYear = Year.now().getValue() + 1;
        int lastYear = Year.now().getValue() - 1;
        return ((lastYear < transactions.getEndDate().getYear()) && (transactions.getEndDate().getYear() < nextYear));
    }

    private boolean validateDaysOffRequest(LocalDate startDate , LocalDate endDate, User user, Long definitionId) {
        int daysRequested = getDayDifference(startDate,endDate);
        int daysTaken = calculateDaysRequested(user , definitionId);
        DaysOffAvailablePK daysOffAvailablePK = new DaysOffAvailablePK();
        daysOffAvailablePK.setDefinition(definitionId);
        daysOffAvailablePK.setUser(user.getId());
        int daysAvailablePerCategory = daysOffRepo.findById(daysOffAvailablePK).orElseThrow(() -> new ValidationFaultException("3222","not days returned for this type")).getTotal();

        return daysAvailablePerCategory < daysTaken + daysRequested;
    }
}
