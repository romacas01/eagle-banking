package com.banking.eagle.service;

import com.banking.eagle.model.Account;
import com.banking.eagle.model.User;
import com.banking.eagle.model.request.CreateAccountRequest;
import com.banking.eagle.repository.AccountRepository;
import com.banking.eagle.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public AccountServiceImpl(AccountRepository accountRepository,
                              UserRepository userRepository,
                              CurrentUserService currentUserService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public Account createAccount(CreateAccountRequest request) {
        User user = currentUserService.getAuthenticatedUser();
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(request.getAccountNumber());

        if(accountOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account with account number " + request.getAccountNumber() + "already exists");
        }

        Account account = Account.builder()
                .accountNumber(request.getAccountNumber())
                .balance(request.getBalance() != null ? request.getBalance() : BigDecimal.ZERO)
                .user(user)
                .build();

        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAccounts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username: " + username + " not found"));

        return accountRepository.findAllByUserId(user.getId());
    }

    @Override
    public Account getAccount(Long accountId) {
        User user = currentUserService.getAuthenticatedUser();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with id: " + accountId + " not found"));

        if (!Objects.equals(account.getUser().getId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this user.");
        }

        return account;
    }
}
