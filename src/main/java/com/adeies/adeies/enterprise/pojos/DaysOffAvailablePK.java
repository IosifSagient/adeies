package com.adeies.adeies.enterprise.pojos;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.entities.User;
import lombok.Data;
import java.io.Serializable;

@Data
public class DaysOffAvailablePK implements Serializable {
    private Long user;
    private Long definition;
}
