package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.EmployeeNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeNoteRepo extends JpaRepository<EmployeeNote, Long> {
    List<EmployeeNote> findEmployeeNoteByManager_IdAndEmployee_Id(Long managerId, Long employeeId);
}
