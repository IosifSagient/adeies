package com.adeies.adeies.enterprise.entities;

import com.adeies.adeies.enterprise.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "TRANSACTIONS")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(referencedColumnName = "id")
    private User approvedBy;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = DaysOffDefinition.class, optional = false)
    @JoinColumn(referencedColumnName = "id")
    private DaysOffDefinition definition;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Status status; // TODO: enum Approved/Rejected/Pending

    @NotNull
    private Integer days;

    private String description;
}
