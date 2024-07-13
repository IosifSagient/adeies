package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.daysOff.InitializeDaysOffRq;
import com.adeies.adeies.enterprise.dto.daysOff.UserDaysOffRq;
import com.adeies.adeies.enterprise.entities.DaysOff;
import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.entities.DaysOffWithDefinitionView;
import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.pojos.DaysOffAvailablePK;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import com.adeies.adeies.enterprise.repository.DaysOffRepo;
import com.adeies.adeies.enterprise.repository.DaysOffWithDefinitionRepo;
import com.adeies.adeies.enterprise.service.DaysOffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DaysOffController {

    private final DaysOffRepo daysOffRepo;
    private final DaysOffService daysOffService;
    private final DaysOffDefinitionRepo daysOffDefinitionRepo;
    private final DaysOffWithDefinitionRepo daysOffWithDefinitionRepo;
    @PostMapping("/initializeDaysOff")
    public ResponseEntity<SuccessResponse> initializeDaysOff(@RequestBody InitializeDaysOffRq rq) {
        daysOffService.initializeDaysOff(rq);
        return ResponseEntity.ok(new SuccessResponse("Days off successfully registered.", null));
    }

    @GetMapping("/getUserDays/{id}")
    public ResponseEntity<List<DaysOffWithDefinitionView>> getUserDays(@PathVariable Long id) {
        List<DaysOffWithDefinitionView> usersDays = daysOffWithDefinitionRepo.getAllDaysByUser(id);
        return new  ResponseEntity<>(usersDays, HttpStatus.OK);
    }

}