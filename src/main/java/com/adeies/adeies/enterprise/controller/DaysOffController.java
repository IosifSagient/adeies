package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.DaysOff.InitializeDaysOffRq;
import com.adeies.adeies.enterprise.entities.DaysOff;
import com.adeies.adeies.enterprise.service.DaysOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class DaysOffController {
    @Autowired
    private DaysOffService daysOffService;

    @PostMapping("/initializeDayOff")
    public ResponseEntity<DaysOff> initializeDaysOff(@RequestBody InitializeDaysOffRq rq) {
        DaysOff rs = daysOffService.initializeDaysOff(rq);
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }


}