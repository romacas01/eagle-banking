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

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account createAccount(CreateAccountRequest request) {
        User user = getUserFromDbById(request.getUserId());

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
        //get the username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //get the user by username
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username: " + username + " not found");
        }
        //get the userId
        List<Account> accounts = accountRepository.findAllByUserId(user.get().getId());
        return accounts;
    }

    private User getUserFromDbById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + userId + " not found");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!user.get().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this user.");
        }

        return user.get();
    }
}
