package com.banking.eagle.model.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @Nonnull
    private Long userId;

    private BigDecimal balance;
}
