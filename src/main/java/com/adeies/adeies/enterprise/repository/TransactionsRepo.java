package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions, Long> {
    List<Transactions> findTransactionsByStatus(Status status);

    List<Transactions> getTransactionsByUser_EmployeeCard_Department_Id(Long deptId);

    List<Transactions> getTransactionsByUser_EmployeeCard_Department_IdAndStatus(Long deptId,

                                                                                 Status status);

    @Query(nativeQuery = true , value = "SELECT COUNT(*) FROM TRANSACTIONS  WHERE USER_ID = :userId")
    Integer getTrxCount(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM TRANSACTIONS WHERE user_id = :userId order by start_date desc", nativeQuery = true)
    Page<Transactions> getTrxByUser (@Param("userId") Long userId, Pageable pageable);
}
