package com.banking.eagle.controller;

import com.banking.eagle.model.Account;
import com.banking.eagle.model.Transaction;
import com.banking.eagle.model.request.AccountResponse;
import com.banking.eagle.model.request.CreateAccountRequest;
import com.banking.eagle.model.request.CreateAccountResponse;
import com.banking.eagle.model.request.CreateTransactionRequest;
import com.banking.eagle.service.AccountService;
import com.banking.eagle.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    private final TransactionService transactionService;

    public AccountController(AccountService accountService,
                             TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
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

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccount(accountId);

        AccountResponse response = AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("{accountId}/transactions")
    public ResponseEntity<AccountResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request, @PathVariable Long accountId) {
        Transaction transaction = transactionService.createTransaction(request, accountId);

        return ResponseEntity.ok(null);
    }

    @GetMapping("{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactions(accountId);

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{accountId}/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long accountId, @PathVariable Long transactionId) {
        Transaction transaction = transactionService.getTransaction(accountId, transactionId);

        return ResponseEntity.ok(null);
    }


}

