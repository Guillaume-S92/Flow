package com.flow.api.transaction;

import com.flow.api.account.BankAccount;
import com.flow.api.account.BankAccountRepository;
import com.flow.api.exception.BankAccountNotFoundException;
import com.flow.api.exception.BankTransactionNotFoundException;
import com.flow.api.transaction.dto.BankTransactionResponse;
import com.flow.api.transaction.dto.CreateBankTransactionRequest;
import com.flow.api.transaction.dto.UpdateBankTransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




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
                .orElseThrow(() -> new BankAccountNotFoundException(request.bankAccountId()));

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

    public BankTransactionResponse getBankTransactionById(Long id) {

        BankTransaction bankTransaction = bankTransactionRepository
                .findById(id)
                .orElseThrow(() -> new BankTransactionNotFoundException(id));

        return new BankTransactionResponse(
                bankTransaction.getId(),
                bankTransaction.getTransactionDetails(),
                bankTransaction.getAmount(),
                bankTransaction.getTransactionType(),
                bankTransaction.getTransactionDate(),
                bankTransaction.getBankAccount().getId()
        );
    }

    public Page<BankTransactionResponse> findAllBankTransactions(Pageable pageable) {

        Page<BankTransaction> bankTransactions =
                bankTransactionRepository.findAllWithBankAccount(pageable);

        return bankTransactions.map(bankTransaction ->
                new BankTransactionResponse(
                        bankTransaction.getId(),
                        bankTransaction.getTransactionDetails(),
                        bankTransaction.getAmount(),
                        bankTransaction.getTransactionType(),
                        bankTransaction.getTransactionDate(),
                        bankTransaction.getBankAccount().getId()
                )
        );
    }

    public void deleteBankTransactionById(Long id) {

        if (!bankTransactionRepository.existsById(id)) {
            throw new BankTransactionNotFoundException(id);
        }

        bankTransactionRepository.deleteById(id);
    }

    @Transactional
    public BankTransactionResponse updateBankTransaction(
            Long id,
            UpdateBankTransactionRequest request
    ) {

        BankTransaction bankTransaction = bankTransactionRepository
                .findById(id)
                .orElseThrow(() -> new BankTransactionNotFoundException(id));

        BankAccount bankAccount = bankAccountRepository
                .findById(request.bankAccountId())
                .orElseThrow(() ->
                        new BankAccountNotFoundException(request.bankAccountId())
                );

        bankTransaction.setTransactionDetails(request.transactionDetails());
        bankTransaction.setAmount(request.amount());
        bankTransaction.setTransactionType(request.transactionType());
        bankTransaction.setTransactionDate(request.transactionDate());
        bankTransaction.setBankAccount(bankAccount);



        return new BankTransactionResponse(
                bankTransaction.getId(),
                bankTransaction.getTransactionDetails(),
                bankTransaction.getAmount(),
                bankTransaction.getTransactionType(),
                bankTransaction.getTransactionDate(),
                bankTransaction.getBankAccount().getId()
        );
    }
}

