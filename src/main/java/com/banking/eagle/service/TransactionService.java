package com.banking.eagle.service;

import com.banking.eagle.model.Transaction;
import com.banking.eagle.model.request.CreateTransactionRequest;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(CreateTransactionRequest request, Long accountId);
    List<Transaction> getTransactions(Long accountId);
    Transaction getTransaction(Long accountId, Long transactionId);
}
