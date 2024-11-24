package com.adeies.adeies.enterprise.dto.daysOff;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateRequestRq {

    @NotNull
    private Long trxId;

    @NotNull
    private RequestDaysOffRq requestDaysOffRq;
}
