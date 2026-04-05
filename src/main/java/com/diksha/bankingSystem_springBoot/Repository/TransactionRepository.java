package com.diksha.bankingSystem_springBoot.Repository;

import com.diksha.bankingSystem_springBoot.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
