package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.DaysOff;
import com.adeies.adeies.enterprise.entities.DaysOffWithDefinitionView;
import com.adeies.adeies.enterprise.pojos.DaysOffAvailablePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaysOffRepo extends JpaRepository<DaysOff, DaysOffAvailablePK> {
    @Query(value = "Select o.*, d.* \n" +
            "From days_off o \n" +
            "Left join days_off_definition d \n" +
            "on o.definition_id = d.id;", nativeQuery = true)
    List<DaysOffWithDefinitionView>  getAllDaysByUser(@Param("id") Long id);
}
