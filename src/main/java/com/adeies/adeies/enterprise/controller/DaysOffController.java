package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.daysOff.InitializeDaysOffRq;
import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.service.DaysOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DaysOffController {
    @Autowired
    private DaysOffService daysOffService;

    @PostMapping("/initializeDaysOff")
    public ResponseEntity<SuccessResponse> initializeDaysOff(@RequestBody InitializeDaysOffRq rq) {
        daysOffService.initializeDaysOff(rq);
        return ResponseEntity.ok(new SuccessResponse("Days off successfully registered.", null));
    }
}