package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TransactionsController {

    @Qualifier("transactionsServiceImpl")
    @Autowired
    private TransactionsService trxService;


    @PostMapping("/requestDaysOff")
    public ResponseEntity<SuccessResponse> requestDaysOff(@RequestBody RequestDaysOffRq rq) {
        trxService.requestDaysOff(rq);
        return new ResponseEntity<>(new SuccessResponse("Day Off Submitted", null), HttpStatus.OK);
    }


//    @GetMapping("/getDepartmentPendingTransactions")
//    public ResponseEntity<SuccessResponse> getDepartmentPendingTransactions(
//            @RequestHeader("Authorization") String jwt) {
//        List<Transactions> transactionsList = trxService.getDepartmentPendingTransactions(jwt);
//        return new ResponseEntity<>(new SuccessResponse("Parta.", transactionsList), HttpStatus.OK);
//    }

}
