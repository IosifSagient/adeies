package com.adeies.adeies.enterprise.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import net.jcip.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Table(name = "days_off_with_definitions")
@Subselect("select uuid() as id, hs.* from days_off_with_definitions hs")
@Immutable
public class DaysOffWithDefinitionView {

    @Id
    @JsonProperty("id")
    private Long id;


    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("available")
    private Integer available;
    @JsonProperty("total")
    private Integer total ;
    @JsonProperty("definitionId")
    private Long definitionId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("wording")
    private String wording;

}
