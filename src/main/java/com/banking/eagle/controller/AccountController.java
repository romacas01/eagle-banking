package com.banking.eagle.controller;

import com.banking.eagle.model.Account;
import com.banking.eagle.model.User;
import com.banking.eagle.model.request.AccountResponse;
import com.banking.eagle.model.request.CreateAccountRequest;
import com.banking.eagle.model.request.CreateAccountResponse;
import com.banking.eagle.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request);

        CreateAccountResponse response = CreateAccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .userId(account.getUser().getId())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        List<AccountResponse> accountsResponse = accounts.stream()
                .map(account -> AccountResponse.builder()
                        .accountNumber(account.getAccountNumber())
                        .balance(account.getBalance()).build()).toList();

        return ResponseEntity.ok(accountsResponse);
    }

}

