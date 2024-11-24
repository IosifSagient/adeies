package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.EmployeeCard;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo
        extends JpaRepository<EmployeeCard, Long>, JpaSpecificationExecutor<EmployeeCard> {
    List<EmployeeCard> findAll(Specification<EmployeeCard> spec);
    @Query(value = "SELECT E.ID  from EMPLOYEE_CARD E WHERE E.DEPARTMENT_ID = :deptId " ,nativeQuery = true)
    List<Long> findAllInDpt(@Param("deptId") Long deptId);
}
