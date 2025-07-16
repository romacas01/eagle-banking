package com.banking.eagle.model.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateTransactionRequest {
    @NotBlank(message = "Type is required")
    private String type;

    @NotNull
    @Positive
    private BigDecimal amount;
}
