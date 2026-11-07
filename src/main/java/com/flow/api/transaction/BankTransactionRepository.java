package com.flow.api.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    boolean existsByBankAccount_Id(Long bankAccountId);

    @Query("""
       SELECT bt
       FROM BankTransaction bt
       JOIN FETCH bt.bankAccount
       """)
    List<BankTransaction> findAllWithBankAccount();
}
