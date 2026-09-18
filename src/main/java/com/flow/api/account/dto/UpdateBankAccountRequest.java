package com.flow.api.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;



public record UpdateBankAccountRequest(

    @NotBlank(message = "Name may not be blank")
    @Size(max = 100, message = "Name may not exceed 100 characters")
    String name,

    @NotNull(message = "Initial balance may not be null")
    BigDecimal initialBalance

    )
{}