package com.adeies.adeies.enterprise.dto.DaysOff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitializeDaysOffRq {

    private Long userId;
    private Long definitionId;
    private Integer total;
    private Integer available;

}
