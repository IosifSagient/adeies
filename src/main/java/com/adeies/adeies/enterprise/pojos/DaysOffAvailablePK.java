package com.adeies.adeies.enterprise.pojos;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import lombok.Data;
import java.io.Serializable;

@Data
public class DaysOffAvailablePK implements Serializable {
    private Long userId;
    private DaysOffDefinition definition;
}
