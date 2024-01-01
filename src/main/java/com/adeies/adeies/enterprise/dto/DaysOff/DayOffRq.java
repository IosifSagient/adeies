package com.adeies.adeies.enterprise.dto.DaysOff;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class DayOffRq {

    @NotNull
    private Long userId;

    @NotNull
    private Long daysOffDefinitionId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private String description;


}
