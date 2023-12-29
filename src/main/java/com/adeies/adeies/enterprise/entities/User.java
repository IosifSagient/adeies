package com.adeies.adeies.enterprise.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private String role;

    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String password;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String language;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = EmployeeCard.class, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private EmployeeCard employeeCard;
}
