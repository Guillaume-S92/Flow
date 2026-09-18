package com.flow.api.account;

import com.flow.api.account.dto.BankAccountResponse;
import com.flow.api.account.dto.CreateBankAccountRequest;
import com.flow.api.account.dto.UpdateBankAccountRequest;
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


    public Optional<BankAccountResponse> findBankAccountById(Long id) {

        Optional<BankAccount> optionalBankAccount= bankAccountRepository.findById(id);

        if(optionalBankAccount.isPresent()){

            BankAccount foundBankAccount = optionalBankAccount.get();

            BankAccountResponse response = new BankAccountResponse(
                    foundBankAccount.getId(),
                    foundBankAccount.getName(),
                    foundBankAccount.getInitialBalance(),
                    foundBankAccount.getCreationDate()
            );

            return Optional.of(response);

        } else {
                return Optional.empty();

        }

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

  public boolean deleteBankAccountById(Long id) {

        if(bankAccountRepository.existsById(id)){
            bankAccountRepository.deleteById(id);
            return true;
        }

        return false;
  }

  public Optional<BankAccountResponse> updateBankAccount(Long id, UpdateBankAccountRequest request) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(id);

      if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.setName(request.name());
            bankAccount.setInitialBalance(request.initialBalance());
            bankAccountRepository.save(bankAccount);

            BankAccountResponse response = new BankAccountResponse(
                    bankAccount.getId(),
                    bankAccount.getName(),
                    bankAccount.getInitialBalance(),
                    bankAccount.getCreationDate()
            );
            return Optional.of(response);

        } else {
            return Optional.empty();}
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
