package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.service.DaysOffDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/management")
@PreAuthorize("hasRole('ADMIN')")
public class DaysOffDefinitionController {
    @Autowired
    private DaysOffDefinitionService daysOffDefinitionService;

    @PostMapping("/createDayOff")
    public ResponseEntity<DaysOffDefinition> createDayOffCategory(
            @RequestBody DaysOffDefinition daysOffDefinition) {
        DaysOffDefinition newCategory = daysOffDefinitionService.createDayOffCategory(
                daysOffDefinition);
        return ResponseEntity.ok(newCategory);
    }

}
