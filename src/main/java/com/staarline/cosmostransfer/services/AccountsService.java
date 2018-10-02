package com.staarline.cosmostransfer.services;

import com.staarline.cosmostransfer.models.Account;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Service
public interface AccountsService {
 
    void createAccount(Account account);
 
    Account getAccount(String accountNumber);
 
    Iterable<Account> getAccounts();
}