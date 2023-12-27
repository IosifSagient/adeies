package com.adeies.adeies.enterprise.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;


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

    @NotBlank
    @NotNull
    private Date startDate;

    @NotBlank
    @NotNull
    private Date endDate;

    @NotBlank
    @NotNull
    private int status; // TODO: enum Approved/Rejected/Pending


}
