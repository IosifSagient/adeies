package com.adeies.adeies.enterprise.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DAYS_OFF_DEFINITION")
public class DaysOffDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "WORDING")
    private String wording;

    @Column(name = "DESCRIPTION")
    private String description;

}
