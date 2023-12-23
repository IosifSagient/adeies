package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.Employee;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{
}
