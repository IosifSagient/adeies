package com.adeies.adeies.enterprise.dto.daysOff;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDaysOffRq {

    private Long userId;
    private DaysOffDefinition daysOffDefinition;
}
