package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions, Long> {
    List<Transactions> findTransactionsByStatus(Status status);

    List<Transactions> getTransactionsByUser_EmployeeCard_Department_Id(Long deptId);

    List<Transactions> getTransactionsByUser_EmployeeCard_Department_IdAndStatus(Long deptId,
                                                                                 Status status);
}
