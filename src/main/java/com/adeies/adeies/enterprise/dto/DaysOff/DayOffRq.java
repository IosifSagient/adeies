package com.adeies.adeies.enterprise.dto.DaysOff;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

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
