package com.flow.api.account;

import com.flow.api.account.dto.BankAccountResponse;
import com.flow.api.account.dto.CreateBankAccountRequest;
import com.flow.api.account.dto.UpdateBankAccountRequest;
import com.flow.api.exception.BankAccountInUseException;
import com.flow.api.exception.BankAccountNotFoundException;
import com.flow.api.transaction.BankTransactionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionRepository bankTransactionRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository, BankTransactionRepository bankTransactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionRepository = bankTransactionRepository;
    }



    public BankAccountResponse findBankAccountById(Long id) {

        BankAccount bankAccount = bankAccountRepository
                .findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));

        return new BankAccountResponse(
                bankAccount.getId(),
                bankAccount.getName(),
                bankAccount.getInitialBalance(),
                bankAccount.getCreationDate()
        );
    }

    public List<BankAccountResponse> findAllBankAccounts() {

        List<BankAccount> bankAccounts =
                bankAccountRepository.findAll();

        List<BankAccountResponse> responses =
                new ArrayList<>();
        for (BankAccount bankAccount : bankAccounts) {
            BankAccountResponse response = new BankAccountResponse(
                    bankAccount.getId(),
                    bankAccount.getName(),
                    bankAccount.getInitialBalance(),
                    bankAccount.getCreationDate()
            );
            responses.add(response);
        }

        return responses;
    }

    public void deleteBankAccountById(Long id) {

        if (!bankAccountRepository.existsById(id)) {
            throw new BankAccountNotFoundException(id);
        }

        if (bankTransactionRepository.existsByBankAccount_Id(id)) {
            throw new BankAccountInUseException(id);
        }

        bankAccountRepository.deleteById(id);
    }

    @Transactional
    public BankAccountResponse updateBankAccount(Long id, UpdateBankAccountRequest request) {

        BankAccount bankAccount = bankAccountRepository
                .findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));

        bankAccount.setName(request.name());
        bankAccount.setInitialBalance(request.initialBalance());


        return new BankAccountResponse(
                bankAccount.getId(),
                bankAccount.getName(),
                bankAccount.getInitialBalance(),
                bankAccount.getCreationDate()
        );
    }

  public BankAccountResponse createBankAccount(CreateBankAccountRequest request) {

      BankAccount bankAccount = new BankAccount(
              request.name(),
              request.initialBalance()
      );

      BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

      return new BankAccountResponse(
              savedBankAccount.getId(),
              savedBankAccount.getName(),
              savedBankAccount.getInitialBalance(),
              savedBankAccount.getCreationDate()
      );

  }

}
