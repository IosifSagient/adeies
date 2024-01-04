package com.adeies.adeies.enterprise.auth;

import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    private Role role;
    
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
