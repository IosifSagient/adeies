package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    List<User> findAll(Specification<User> spec);

    Optional<User> findByEmail(String email);


//    @Query(value = "SELECT * FROM USERS u WHERE (EMPLOYEE_CARD_ID IN (SELECT ID FROM EMPLOYEE_CARD WHERE DEPARTMENT_ID = ?1)AND ROLE = 2) OR ROLE = 1", nativeQuery = true)
//    List<User> findAllByDeptId(Long deptId);
//    // select emplpoyeeID from employeecard where department.id = tade
//

}
