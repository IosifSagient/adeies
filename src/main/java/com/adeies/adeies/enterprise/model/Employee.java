package com.adeies.adeies.enterprise.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table( name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "EMPLOYEE_ID")
    private Long employee_id;

    @Column (name = "FIRST_NAME")
    private String firstname;

    @Column(name = "LAST_NAME")
    private String lastname;

    private String email;
    @Column( name = "passwords")
    private String passwords;
    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "marital_status",length = 30)
    private String maritalStatus;
}
