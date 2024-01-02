package com.adeies.adeies.enterprise.repository;

import com.adeies.adeies.enterprise.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepo extends JpaRepository<Transactions, Long> {

}
