package com.flow.api.account;

import com.flow.api.account.dto.BankAccountResponse;
import com.flow.api.account.dto.CreateBankAccountRequest;
import com.flow.api.account.dto.UpdateBankAccountRequest;
import com.flow.api.exception.BankAccountNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
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

        bankAccountRepository.deleteById(id);
    }

    public BankAccountResponse updateBankAccount(Long id, UpdateBankAccountRequest request) {

        BankAccount bankAccount = bankAccountRepository
                .findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));

        bankAccount.setName(request.name());
        bankAccount.setInitialBalance(request.initialBalance());

        bankAccountRepository.save(bankAccount);

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
