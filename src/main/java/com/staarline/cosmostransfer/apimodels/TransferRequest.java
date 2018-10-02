package com.staarline.cosmostransfer.apimodels;

import java.io.Serializable;
 
public class TransferRequest implements Serializable {
    private String fromAccount;
    private String toAccount;
    private double amount;
 
    public String getFromAccount() {
        return fromAccount;
    }
 
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }
 
    public String getToAccount() {
        return toAccount;
    }
 
    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }
 
    public double getAmount() {
        return amount;
    }
 
    public void setAmount(double amount) {
        this.amount = amount;
    }
}