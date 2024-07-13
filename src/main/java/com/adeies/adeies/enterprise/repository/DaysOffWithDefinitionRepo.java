package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.DaysOffWithDefinitionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaysOffWithDefinitionRepo extends PagingAndSortingRepository<DaysOffWithDefinitionView,Long> {

    @Query(value = "SELECT * FROM days_off_with_definitions  WHERE user_id= :id ", nativeQuery = true)
    List<DaysOffWithDefinitionView>  getAllDaysByUser(@Param("id") Long id);
}
