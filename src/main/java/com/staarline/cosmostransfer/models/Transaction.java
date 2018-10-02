package com.staarline.cosmostransfer.models;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Document(collection = "transactions")
public class Transaction implements Serializable {
 
    @Id
    private String reference;
 
    private double amount;
 
    private String accountNumber;
 
    public Transaction(double amount, String accountNumber) {
        this.amount = amount;
        this.accountNumber = accountNumber;
    }
 
    public Transaction() {
 
    }
 
    public String getReference() {
        return reference;
    }
 
    public double getAmount() {
        return amount;
    }
 
    public void setAmount(double amount) {
        this.amount = amount;
    }
 
    public String getAccountNumber() {
        return accountNumber;
    }
 
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}