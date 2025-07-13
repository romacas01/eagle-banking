package com.banking.eagle.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class CreateAccountResponse {
    private String accountNumber;
    private BigDecimal balance;
    private Long userId;
}
