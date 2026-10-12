package com.flow.api.transaction;

import com.flow.api.account.BankAccount;
import com.flow.api.account.BankAccountRepository;
import com.flow.api.transaction.dto.BankTransactionResponse;
import com.flow.api.transaction.dto.CreateBankTransactionRequest;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public BankTransactionService(
            BankTransactionRepository bankTransactionRepository,
            BankAccountRepository bankAccountRepository
    ) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankTransactionResponse createBankTransaction(CreateBankTransactionRequest request) {

        BankAccount bankAccount = bankAccountRepository
                .findById(request.bankAccountId())
                .orElseThrow();

        BankTransaction bankTransaction = new BankTransaction(
                request.transactionDetails(),
                request.amount(),
                request.transactionType(),
                request.transactionDate(),
                bankAccount
        );

        BankTransaction savedBankTransaction =
                bankTransactionRepository.save(bankTransaction);

        return new BankTransactionResponse(
                savedBankTransaction.getId(),
                savedBankTransaction.getTransactionDetails(),
                savedBankTransaction.getAmount(),
                savedBankTransaction.getTransactionType(),
                savedBankTransaction.getTransactionDate(),
                savedBankTransaction.getBankAccount().getId()
        );
    }
}