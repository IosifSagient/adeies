package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysOffDefinitionRepo extends JpaRepository<DaysOffDefinition, Long> {
}
