package com.flow.api.transaction.dto;

import com.flow.api.transaction.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateBankTransactionRequest(

        @NotBlank(message = "Transaction details may not be blank")
        @Size(max = 100, message = "Transaction details may not exceed 100 characters")
        String transactionDetails,

        @NotNull(message = "Amount may not be null")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Transaction type may not be null")
        TransactionType transactionType,

        @NotNull(message = "Transaction date may not be null")
        LocalDateTime transactionDate,

        @NotNull(message = "Bank account id may not be null")
        Long bankAccountId

) {
}