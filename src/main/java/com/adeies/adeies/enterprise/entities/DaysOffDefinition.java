package com.adeies.adeies.enterprise.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DAYS_OFF_DEFINITION")
public class DaysOffDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String wording;
    private String description;
}
