package com.adeies.adeies.enterprise.entities;

import com.adeies.adeies.enterprise.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonIgnore
    private Role role;

    @NotBlank
    @NotNull
    @JsonIgnore
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

    @Builder.Default
    private LocalDateTime credentialsExpiryDate = LocalDateTime.now().plusDays(120);

    private Set<Role> roles = new HashSet<>(List.of(Role.values()));

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // TODO: Implement expiration date reminder and password change etc...
    public boolean isCredentialsNonExpired() {
        LocalDateTime now = LocalDateTime.now();
        return this.credentialsExpiryDate.isAfter(now);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
