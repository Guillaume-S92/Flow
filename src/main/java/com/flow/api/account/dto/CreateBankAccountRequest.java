package com.flow.api.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateBankAccountRequest {

    @NotBlank(message = "Name may not be blank")
    @Size(max = 100, message = "Name may not exceed 100 characters")
    private String name;
    @NotNull(message = "Initial balance may not be null")
    private BigDecimal initialBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}