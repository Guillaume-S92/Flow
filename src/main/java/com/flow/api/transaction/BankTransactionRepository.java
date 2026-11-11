package com.flow.api.transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;



public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    boolean existsByBankAccount_Id(Long bankAccountId);

    @Query(value = """
       SELECT bt
       FROM BankTransaction bt
       JOIN FETCH bt.bankAccount
       """,
       countQuery = """
        SELECT COUNT (bt)
        FROM BankTransaction bt       
       """
    )

    Page<BankTransaction> findAllWithBankAccount(Pageable pageable);
}
