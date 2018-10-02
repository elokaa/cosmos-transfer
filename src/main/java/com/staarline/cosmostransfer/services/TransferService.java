package com.staarline.cosmostransfer.services;

import com.staarline.cosmostransfer.exceptions.NotFoundException;
import com.staarline.cosmostransfer.exceptions.TransferServiceException;
import org.springframework.stereotype.Service;
 
@Service
public interface TransferService {
    void transfer(String fromAccount, String toAccount, double amount) throws TransferServiceException, NotFoundException;
}