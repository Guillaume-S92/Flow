package com.flow.api.account;

import com.flow.api.account.dto.BankAccountResponse;
import com.flow.api.account.dto.CreateBankAccountRequest;
import com.flow.api.account.dto.UpdateBankAccountRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountRepository bankAccountRepository) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public BankAccountResponse addBankAccount(
            @Valid @RequestBody CreateBankAccountRequest request
    ) {
        return bankAccountService.createBankAccount(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponse> getBankAccountById(
            @PathVariable Long id
    ) {
        Optional<BankAccountResponse> bankAccount =
                bankAccountService.findBankAccountById(id);

        if (bankAccount.isPresent()) {
            return ResponseEntity.ok(bankAccount.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BankAccountResponse>> getAllBankAccounts() {
        return ResponseEntity.ok(bankAccountService.findAllBankAccounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountResponse> updateBankAccount(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBankAccountRequest request
    ) {
        Optional<BankAccountResponse>  updateBankAccount =
                bankAccountService.updateBankAccount(id, request);

        if (updateBankAccount.isPresent()) {
            return ResponseEntity.ok(updateBankAccount.get());
        } else  {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccountById(
            @PathVariable Long id
    ) {
        boolean deleted = bankAccountService.deleteBankAccountById(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
