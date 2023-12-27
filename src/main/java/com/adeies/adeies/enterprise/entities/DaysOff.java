package com.adeies.adeies.enterprise.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name = "DAYS_OFF")
public class DaysOff {
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    @Id
    private User user;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = DaysOffDefinition.class, optional = false)
    @JoinColumn(referencedColumnName = "id")
    @Id
    private DaysOffDefinition definition;

    @NotBlank
    @NotNull
    private Long available;

    @NotBlank
    @NotNull
    @Min(value = 1)
    private Long total;
}
