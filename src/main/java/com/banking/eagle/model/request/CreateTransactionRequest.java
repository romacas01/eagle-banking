package com.banking.eagle.model.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTransactionRequest {
    @NotBlank(message = "Type is required")
    private String type;

    @Nonnull
    private Double amount;
}
