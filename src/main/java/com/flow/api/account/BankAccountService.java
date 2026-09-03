package com.flow.api.account;

import com.flow.api.account.dto.BankAccountResponse;
import com.flow.api.account.dto.CreateBankAccountRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createBankAccount(String name, BigDecimal balance) {
        BankAccount bankAccount = new BankAccount(name,balance);

        return bankAccountRepository.save(bankAccount);
    }

   public Optional<BankAccount> findBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
   }

   public List<BankAccount> findAllBankAccount(){
        return bankAccountRepository.findAll();
   }

  public void deleteBankAccountById(Long id) {
        bankAccountRepository.deleteById(id);
    }

  public Optional<BankAccount> updateBankAccount(Long id, String name, BigDecimal initialBalance) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(id);

      if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.setName(name);
            bankAccount.setInitialBalance(initialBalance);
            bankAccountRepository.save(bankAccount);

            return Optional.of(bankAccount);

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
