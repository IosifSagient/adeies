package com.adeies.adeies.enterprise.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@Entity
@Table( name = "EMPLOYEE_CARD")
public class EmployeeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private Date birthdate;

    @OneToOne(targetEntity = Department.class)
    @JoinColumn(referencedColumnName = "id")
    private Department department;

    private String position;

    private String area;

    @Length(min = 0, max = 30)
    private String maritalStatus;
}
