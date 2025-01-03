package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.DateRange;
import com.adeies.adeies.enterprise.dto.Transactions.EmployeeTrxFilters;
import com.adeies.adeies.enterprise.dto.Transactions.TransactionsDTO;
import com.adeies.adeies.enterprise.dto.Transactions.TrxStatusUpdate;
import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import com.adeies.adeies.enterprise.dto.daysOff.UpdateRequestRq;
import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.Status;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.mappers.TrxDisplayMapper;
import com.adeies.adeies.enterprise.repository.TransactionsRepo;
import com.adeies.adeies.enterprise.service.TransactionsService;
import com.adeies.adeies.enterprise.utils.TransactionsSpecification;
import com.adeies.adeies.enterprise.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionsController {

    @Qualifier("transactionsServiceImpl")
    @Autowired
    private TransactionsService trxService;

    @Autowired
    private TransactionsRepo trxRepo;

    @Autowired
    private TrxDisplayMapper trxDisplayMapper;

    @Autowired
    private UserUtils userUtils;

    @PostMapping("/request/days-off")
    public ResponseEntity<SuccessResponse> requestDaysOff(@RequestBody RequestDaysOffRq rq,
                                                          @AuthenticationPrincipal OAuth2User user) {
        trxService.requestDaysOff(rq, user);
        return new ResponseEntity<>(
                new SuccessResponse("Day off request successfully submitted.", null),
                HttpStatus.OK);
    }

    @PutMapping("/request/update-day-off")
    public ResponseEntity<SuccessResponse> updateDayOffRequest(@RequestBody UpdateRequestRq rq,
                                                               @AuthenticationPrincipal OAuth2User user){
        trxService.updateDayOffRequest(rq,user);

        return new ResponseEntity<>(new SuccessResponse("The Request was Updated",null),HttpStatus.OK);
    }

    @GetMapping("/get/free")
    public ResponseEntity<SuccessResponse> free(
            @RequestHeader(value = "status", required = false) Status status, Authentication auth) {
        System.out.println("auth.lol??? " + auth);
        List<Transactions> transactionsList = status != null ? trxRepo.findTransactionsByStatus(
                status) : trxRepo.findAll();
        return ResponseEntity.ok(new SuccessResponse("Parta.", transactionsList));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/get/all")
    public ResponseEntity<SuccessResponse> getAllTransactions(
            @RequestHeader(value = "status", required = false) Status status) {
        List<Transactions> transactionsList = status != null ? trxRepo.findTransactionsByStatus(
                status) : trxRepo.findAll();
        return ResponseEntity.ok(new SuccessResponse("Parta.", transactionsList));
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @GetMapping("/get/by-department/custom")
    public ResponseEntity<SuccessResponse> getDepartmentPendingTransactionsWithCustomAuth(
            @RequestHeader(value = "status", required = false) Status status, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Long deptId = user.getEmployeeCard().getDepartment().getId();
        List<Transactions> transactionsList = status != null ? trxRepo.getTransactionsByUser_EmployeeCard_Department_IdAndStatus(
                deptId, status) : trxRepo.getTransactionsByUser_EmployeeCard_Department_Id(deptId);
        return ResponseEntity.ok(new SuccessResponse("Parta.", transactionsList));
    }

    @GetMapping("/get/by-department")
    public List<TransactionsDTO> getDepartmentPendingTransactionsWithOauth2(
            @AuthenticationPrincipal OAuth2User oAuth2User) {
        User user = userUtils.getUserFromOAuth(oAuth2User);
        return trxService.getTrxByDepartment(user, Pageable.unpaged(),null);
    }

    @GetMapping("/get/userReqs/{id}/{page}/{size}")
    public List<TransactionsDTO> getUsersTransactions(@PathVariable Long id, @PathVariable int page , @PathVariable int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Transactions> allReqs = trxRepo.getTrxByUser(id,pageable);
        System.out.println("kati "  + allReqs.getContent());
        return allReqs.getContent().stream().map(trx -> trxDisplayMapper.toDto(trx)).collect(Collectors.toList());
    }

    @GetMapping("get/trxCount/{id}")
    public Integer getTrxCount(@PathVariable Long id){
        return trxRepo.getTrxCount(id);
    }

    @GetMapping("get-by-id/{id}")
    public Transactions getTrxById (@PathVariable Long id) {
        return trxRepo.findById(id).orElseThrow(() -> new ValidationFaultException("010101", "TRX NOT FOUND"));
    }

    @GetMapping("getRequests-By-User-And-Type/{definitionId}")
    public Integer getRequestedDaysPerDayOffType(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable("definitionId") Long definitionId){
        User user = userUtils.getUserFromOAuth(oAuth2User);
        return trxService.calculateDaysRequested(user, definitionId);
    }

    @PostMapping("updateTrxStatus")
    public SuccessResponse updateStatusTrx(@RequestBody TrxStatusUpdate trxStatusUpdate, @AuthenticationPrincipal OAuth2User oAuth2User) {
        User user = userUtils.getUserFromOAuth(oAuth2User);
        trxService.updateTrxStatus(trxStatusUpdate ,user);
        return new SuccessResponse("Status updated ", null);
    }
    @GetMapping("get/accepted/by-department")
    public List<TransactionsDTO> getAcceptedTrx(@AuthenticationPrincipal OAuth2User oAuth2User) {
        User user = userUtils.getUserFromOAuth(oAuth2User);
        List<Status> statuses = List.of(Status.ACCEPTED);
        return trxService.getTrxByDepartment(user, Pageable.unpaged(), statuses);
    }
    //get/accepted/by-department

    @PostMapping("get/filteredTrx")
    public ResponseEntity<SuccessResponse<List<TransactionsDTO>>> getFilteredTrx(@RequestBody EmployeeTrxFilters employeeTrxFilters){
        Specification<Transactions> spec = TransactionsSpecification.buildSpecificationDependingOnCriteria(employeeTrxFilters) ;
        List<TransactionsDTO> trx = trxRepo.findAll(spec).stream().map(transactions -> trxDisplayMapper.toDto(transactions)).toList();
        return ResponseEntity.ok(new SuccessResponse<>("parta data sou ", trx));
    }
}
