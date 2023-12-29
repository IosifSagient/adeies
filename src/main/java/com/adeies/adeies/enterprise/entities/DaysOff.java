package com.adeies.adeies.enterprise.entities;

import com.adeies.adeies.enterprise.pojos.DaysOffAvailablePK;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name = "DAYS_OFF")
@IdClass(DaysOffAvailablePK.class)
public class DaysOff {
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    @Id
    private User user;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = DaysOffDefinition.class, optional = false)
    @JoinColumn(referencedColumnName = "id")
    @Id
    private DaysOffDefinition definition;


    @NotNull
    private Integer available;


    @NotNull
    @Min(value = 1)
    private Integer total;
}

