package com.banking.eagle.service;

import com.banking.eagle.model.Account;
import com.banking.eagle.model.Transaction;
import com.banking.eagle.model.TransactionType;
import com.banking.eagle.model.User;
import com.banking.eagle.model.request.CreateTransactionRequest;
import com.banking.eagle.repository.AccountRepository;
import com.banking.eagle.repository.TransactionRepository;
import com.banking.eagle.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private final CurrentUserService currentUserService;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountRepository accountRepository,
                                  UserRepository userRepository,
                                  CurrentUserService currentUserService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    @Transactional
    public Transaction createTransaction(CreateTransactionRequest request, Long accountId) {
        Account account = checkIfAccountFromUser(accountId);

        if (request.getType().equalsIgnoreCase(TransactionType.DEPOSIT.getValue())) {
            account.setBalance(account.getBalance().add(request.getAmount()));
        }

        if (request.getType().equalsIgnoreCase(TransactionType.WITHDRAW.getValue())) {
            if (account.getBalance().compareTo(request.getAmount()) >= 0) {
                account.setBalance(account.getBalance().subtract(request.getAmount()));
            } else {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "There are not enough funds in the account to perform transaction.");

            }
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType(TransactionType.findByKey(request.getType().toLowerCase()));
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(LocalDateTime.now());

        accountRepository.save(account);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactions(Long accountId) {
        Account account = checkIfAccountFromUser(accountId);

        return account.getTransactions();
    }

    @Override
    public Transaction getTransaction(Long accountId, Long transactionId) {
        Account account = checkIfAccountFromUser(accountId);

        return transactionRepository.findByAccountIdAndId(account, transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    private Account checkIfAccountFromUser(Long accountId) {
        User authenticatedUser = currentUserService.getAuthenticatedUser();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (!Objects.equals(account.getUser().getId(), authenticatedUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this account.");
        }

        return account;
    }
}
