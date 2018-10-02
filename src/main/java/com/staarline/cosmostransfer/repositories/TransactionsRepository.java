package com.staarline.cosmostransfer.repositories;

import com.microsoft.azure.spring.data.documentdb.repository.DocumentDbRepository;
import com.staarline.cosmostransfer.models.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends DocumentDbRepository<Transaction, Long> {
    Transaction findByAccountNumber(String accountNumber);
}