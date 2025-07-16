package com.banking.eagle.repository;


import com.banking.eagle.model.Account;
import com.banking.eagle.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.account = ?1 and t.id = ?2")
    Optional<Transaction> findByAccountIdAndId(Account account, Long transactionId);
}
