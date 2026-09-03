package com.flow.api.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BankAccountResponse(
        Long id,
        String name,
        BigDecimal initialBalance,
        LocalDateTime creationDate
) {
}
