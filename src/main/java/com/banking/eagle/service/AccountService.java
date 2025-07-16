package com.banking.eagle.service;

import com.banking.eagle.model.Account;
import com.banking.eagle.model.request.CreateAccountRequest;

import java.util.List;

public interface AccountService {
    Account createAccount(CreateAccountRequest request);
    List<Account> getAccounts();
    Account getAccount(Long accountId);
}
