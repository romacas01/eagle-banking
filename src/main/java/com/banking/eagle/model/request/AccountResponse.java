package com.banking.eagle.model.request;

import com.banking.eagle.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class AccountResponse {

    private String accountNumber;
    private BigDecimal balance;
}
