package com.staarline.cosmostransfer.services;

import com.staarline.cosmostransfer.exceptions.NotFoundException;
import com.staarline.cosmostransfer.exceptions.TransferServiceException;
import com.staarline.cosmostransfer.models.Account;
import com.staarline.cosmostransfer.models.Transaction;
import com.staarline.cosmostransfer.repositories.AccountBalanceDao;
import com.staarline.cosmostransfer.repositories.AccountBalanceRepository;
import com.staarline.cosmostransfer.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
@Component
public class TransferServiceImpl implements TransferService {
 
    private final TransactionsRepository transactionsRepository;
 
    private final AccountBalanceDao accountBalanceDao;

    private final AccountBalanceRepository accountBalanceRepository;

    @Autowired
    public TransferServiceImpl(TransactionsRepository transactionsRepository, AccountBalanceRepository accountBalanceRepository, AccountBalanceDao accountBalanceDao) {
        this.transactionsRepository = transactionsRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.accountBalanceDao = accountBalanceDao;
    }
 
    @Override
    @Transactional
    public void transfer(String fromAccount, String toAccount, double amount) throws TransferServiceException, NotFoundException {
        if (fromAccount.equals(toAccount)) {
            throw new TransferServiceException("Cannot transfer to same account");
        }

        if (amount < 100) {
            throw new TransferServiceException("Amount cannot be less than #100");
        }
 
        //Fetch accounts
        Account transferringAccount = accountBalanceRepository.findById(fromAccount).orElseThrow(
                () -> new NotFoundException("No account found with account number: " + fromAccount));

        Account receivingAccount = accountBalanceRepository.findById(toAccount).orElseThrow(
                () -> new NotFoundException("No account found with account number: " + toAccount));
 
        //Generate new transaction
        Transaction transaction = validateAccountForTransaction(fromAccount, amount, transferringAccount);
 
        //Update account balances
        double balance = transferringAccount.getBalance() - amount;
        transferringAccount.setBalance(balance);
        receivingAccount.setBalance(receivingAccount.getBalance() + amount);
 
        //Persist account balances
        accountBalanceRepository.save(transferringAccount);
        accountBalanceRepository.save(receivingAccount);
 
        //Persist transactions
        transactionsRepository.save(transaction);
    }

    private Transaction validateAccountForTransaction(String fromAccount, double amount, Account transferringAccount) throws TransferServiceException {
        if (transferringAccount.getBalance() < amount) {
            throw new TransferServiceException("Insufficient funds");
        }
 
        return new Transaction(amount, fromAccount);
    }
}