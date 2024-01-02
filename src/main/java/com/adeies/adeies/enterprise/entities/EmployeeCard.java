package com.adeies.adeies.enterprise.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@Entity
@Table(name = "EMPLOYEE_CARD")
public class EmployeeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank()
    @NotNull()
    private String firstName;

    @NotBlank()
    @NotNull()
    private String lastName;

    @NotNull()
    private Date birthDate;

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(referencedColumnName = "id")
    private Department department;

    @NotBlank()
    @NotNull()
    private String position;

    @NotBlank()
    @NotNull()
    private String area;

    @NotBlank()
    @NotNull()
    @Length(min = 1, max = 30)
    private String maritalStatus;


}
