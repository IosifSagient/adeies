package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.Transactions.TransactionsDto;
import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.Status;
import com.adeies.adeies.enterprise.mappers.TrxDisplayMapper;
import com.adeies.adeies.enterprise.repository.TransactionsRepo;
import com.adeies.adeies.enterprise.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/request/days-off")
    public ResponseEntity<SuccessResponse> requestDaysOff(@RequestBody RequestDaysOffRq rq,
                                                          @AuthenticationPrincipal User user) {
        trxService.requestDaysOff(rq, user);
        return new ResponseEntity<>(
                new SuccessResponse("Day off request successfully submitted.", null),
                HttpStatus.OK);
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
    @GetMapping("/get/by-department")
    public ResponseEntity<SuccessResponse> getDepartmentPendingTransactions(
            @RequestHeader(value = "status", required = false) Status status, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Long deptId = user.getEmployeeCard().getDepartment().getId();
        List<Transactions> transactionsList = status != null ? trxRepo.getTransactionsByUser_EmployeeCard_Department_IdAndStatus(
                deptId, status) : trxRepo.getTransactionsByUser_EmployeeCard_Department_Id(deptId);
        return ResponseEntity.ok(new SuccessResponse("Parta.", transactionsList));
    }

    @GetMapping("/get/userReqs/{id}/{page}/{size}")
    public List<TransactionsDto> getUsersTransactions(@PathVariable Long id, @PathVariable int page , @PathVariable int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Transactions> allReqs = trxRepo.getTrxByUser(id,pageable);
        System.out.println("kati "  + allReqs.getContent());
        return allReqs.getContent().stream().map(trx -> trxDisplayMapper.toDto(trx)).collect(Collectors.toList());
    }

    @GetMapping("get/trxCount/{id}")
    public Integer getTrxCount(@PathVariable Long id){
        return trxRepo.getTrxCount(id);
    }
}
