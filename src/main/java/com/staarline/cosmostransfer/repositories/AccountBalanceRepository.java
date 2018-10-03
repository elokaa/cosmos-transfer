package com.staarline.cosmostransfer.repositories;

import com.microsoft.azure.spring.data.documentdb.repository.DocumentDbRepository;
import com.staarline.cosmostransfer.models.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceRepository extends DocumentDbRepository<Account, String> {

}