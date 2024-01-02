package com.adeies.adeies.enterprise.dto.daysOff;

import com.adeies.adeies.enterprise.entities.DaysOff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitializeDaysOffRq {
    private Long userId;
    private List<DaysOff> daysOff;
//    private Long definitionId;
//    private Integer total;
//    private Integer available;
}
