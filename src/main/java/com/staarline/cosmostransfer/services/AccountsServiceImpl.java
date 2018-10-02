package com.staarline.cosmostransfer.services;

import com.staarline.cosmostransfer.models.Account;
import com.staarline.cosmostransfer.repositories.AccountBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
 
@Component
public class AccountsServiceImpl implements AccountsService {
    private final AccountBalanceRepository accountBalanceRepository;
 
    @Autowired
    public AccountsServiceImpl(AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }
 
    @Override
    public void createAccount(Account account) {
        accountBalanceRepository.save(account);
    }
 
    @Override
    @Nullable
    public Account getAccount(String accountNumber) {
        return accountBalanceRepository.findByAccountNumber(accountNumber);
    }
 
    @Override
    public Iterable<Account> getAccounts() {
        return accountBalanceRepository.findAll();
    }
}