package com.staarline.cosmostransfer.controllers;

import com.staarline.cosmostransfer.models.Account;
import com.staarline.cosmostransfer.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping(path = "/api/v1/account/")
public class AccountsController {
 
    private final AccountsService accountsService;
 
    @Autowired
    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }
 
    @PostMapping(path = "new")
    public boolean createAccount(@RequestBody Account account) {
        accountsService.createAccount(account);
        return true;
    }
 
    @GetMapping(path = "all")
    public Iterable<Account> getAccounts() {
        return accountsService.getAccounts();
    }
}