package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaysOffDefinitionRepo extends JpaRepository<DaysOffDefinition, Long> {

    @Query(nativeQuery = true, value = "SELECT public.storedDaysOff()")
    String storedDaysOff();

    @Query("SELECT new com.adeies.adeies.enterprise.entities.DaysOffDefinition(d.id, d.wording, d.description, d.date_create, d.user_create, new com.adeies.adeies.enterprise.entities.Testaki(t.year, t.brand, t.model, t.date_create, t.user_create)) " + "FROM DaysOffDefinition d " + "LEFT JOIN Testaki t ON d.id = t.year")
    List<DaysOffDefinition> customJavaJoin();


}
