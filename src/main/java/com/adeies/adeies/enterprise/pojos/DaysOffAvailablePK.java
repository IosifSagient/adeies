package com.adeies.adeies.enterprise.pojos;

import lombok.Data;
import java.io.Serializable;

@Data
public class DaysOffAvailablePK implements Serializable {
    private Long user;
    private Long definition;
}
