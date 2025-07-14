package com.banking.eagle.service;

import com.banking.eagle.model.Transaction;
import com.banking.eagle.model.request.CreateTransactionRequest;

public interface TransactionService {
    Transaction createTransaction(CreateTransactionRequest request, Long accountId);
}
