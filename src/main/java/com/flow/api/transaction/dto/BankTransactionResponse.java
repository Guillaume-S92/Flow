package com.flow.api.transaction.dto;

import com.flow.api.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BankTransactionResponse(
        Long id,
        String transactionDetails,
        BigDecimal amount,
        TransactionType transactionType,
        LocalDateTime transactionDate,
        Long bankAccountId
) {
}