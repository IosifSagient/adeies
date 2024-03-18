package com.adeies.adeies.enterprise.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "testaki")
public class Testaki {
    @Id
    private Long year;

    private String brand;

    private String model;

    private Date date_create;

    private String user_create;
}
