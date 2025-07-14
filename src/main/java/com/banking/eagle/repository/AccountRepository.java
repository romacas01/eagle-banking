package com.banking.eagle.repository;


import com.banking.eagle.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String number);
    List<Account> findAllByUserId(Long userId);

    Optional<Account> findById(Long accountId);
}
