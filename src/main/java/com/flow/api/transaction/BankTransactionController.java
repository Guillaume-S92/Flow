package com.flow.api.transaction;

import com.flow.api.account.BankAccountService;
import com.flow.api.transaction.dto.BankTransactionResponse;
import com.flow.api.transaction.dto.CreateBankTransactionRequest;
import com.flow.api.transaction.dto.UpdateBankTransactionRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bank-transaction")
public class BankTransactionController {

    @Autowired
    BankTransactionService bankTransactionService;

    @PostMapping
    public ResponseEntity<BankTransactionResponse> addBankTransaction(@Valid @RequestBody CreateBankTransactionRequest request) {
        BankTransactionResponse response = bankTransactionService.createBankTransaction(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BankTransactionResponse> getBankTransactionById(@PathVariable Long id) {
        Optional<BankTransactionResponse> bankTransaction = bankTransactionService.getBankTransactionById(id);
        if (bankTransaction.isPresent()) {
            return ResponseEntity.ok(bankTransaction.get());
        } else  {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<BankTransactionResponse>> getAllBankTransactions() {
        return ResponseEntity.ok(bankTransactionService.findAllBankTransactions());
    }


    @PutMapping("/{id}")
    public ResponseEntity<BankTransactionResponse> updateBankTransaction(@PathVariable Long id, @Valid @RequestBody UpdateBankTransactionRequest request) {
        Optional<BankTransactionResponse> updatebankTransaction = bankTransactionService.updateBankTransaction(id, request);

        if (updatebankTransaction.isPresent()) {
            return ResponseEntity.ok(updatebankTransaction.get());
        } else   {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BankTransactionResponse> deleteBankTransaction(@PathVariable Long id) {
        boolean canDelete = bankTransactionService.deleteBankTransactionById(id);

        if (canDelete) {
            return ResponseEntity.ok().build();
        } else   {
            return ResponseEntity.notFound().build();
        }
    }
}
