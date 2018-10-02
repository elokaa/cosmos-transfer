package com.staarline.cosmostransfer.models;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Document(collection = "balances")
public class Account implements Serializable {
 
    @Id
    @NotNull
    @Size(min = 10, max = 10)
    private String accountNumber;
 
    private double balance;
 
    public Account(@NotNull @Size(min = 10, max = 10) String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
 
    public Account() {
 
    }
 
    public String getAccountNumber() {
        return accountNumber;
    }
 
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
 
    public double getBalance() {
        return balance;
    }
 
    public void setBalance(double balance) {
        this.balance = balance;
    }
}