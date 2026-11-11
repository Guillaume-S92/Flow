package com.flow.api.transaction;

import com.flow.api.account.BankAccountService;
import com.flow.api.transaction.dto.BankTransactionResponse;
import com.flow.api.transaction.dto.CreateBankTransactionRequest;
import com.flow.api.transaction.dto.UpdateBankTransactionRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bank-transactions")
public class BankTransactionController {


    private final BankTransactionService bankTransactionService;

    public BankTransactionController(
            BankTransactionService bankTransactionService
    ) {
        this.bankTransactionService = bankTransactionService;
    }

    @PostMapping
    public ResponseEntity<BankTransactionResponse> addBankTransaction(@Valid @RequestBody CreateBankTransactionRequest request) {
        BankTransactionResponse response = bankTransactionService.createBankTransaction(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BankTransactionResponse> getBankTransactionById(
            @PathVariable Long id
    ) {
        BankTransactionResponse bankTransaction = bankTransactionService.getBankTransactionById(id);

        return ResponseEntity.ok(bankTransaction);
    }

    @GetMapping
    public ResponseEntity<Page<BankTransactionResponse>> getAllBankTransactions(Pageable pageable) {
        return ResponseEntity.ok(bankTransactionService.findAllBankTransactions(pageable));
    }


    @PutMapping("/{id}")
    public ResponseEntity<BankTransactionResponse> updateBankTransaction(@PathVariable Long id, @Valid @RequestBody UpdateBankTransactionRequest request) {

        BankTransactionResponse updatedBankTransaction = bankTransactionService.updateBankTransaction(id, request);

        return ResponseEntity.ok(updatedBankTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankTransaction(@PathVariable Long id) {

        bankTransactionService.deleteBankTransactionById(id);

        return ResponseEntity.noContent().build();
    }
}
