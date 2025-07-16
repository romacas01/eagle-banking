package com.banking.eagle.model.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull
    private Long userId;

    private BigDecimal balance;
}
