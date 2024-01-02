package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionsController {

    @Qualifier("transactionsServiceImpl")
    @Autowired
    private TransactionsService trxService;


    @GetMapping("/getDepartmentPendingTransactions")
    public ResponseEntity<SuccessResponse> getDepartmentPendingTransactions(
            @RequestHeader("Authorization") String jwt) {
        List<Transactions> transactionsList = trxService.getDepartmentPendingTransactions(jwt);
        return new ResponseEntity<>(new SuccessResponse("Parta.", transactionsList), HttpStatus.OK);
    }
}
