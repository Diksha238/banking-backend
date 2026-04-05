package com.diksha.bankingSystem_springBoot.Repository;

import com.diksha.bankingSystem_springBoot.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
