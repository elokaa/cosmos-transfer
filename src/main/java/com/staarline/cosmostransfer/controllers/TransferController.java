package com.staarline.cosmostransfer.controllers;

import com.staarline.cosmostransfer.apimodels.TransferRequest;
import com.staarline.cosmostransfer.exceptions.NotFoundException;
import com.staarline.cosmostransfer.exceptions.TransferServiceException;
import com.staarline.cosmostransfer.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/transfer")
public class TransferController {
 
    private final TransferService transferService;
 
    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }
 
    @PostMapping
    public boolean transfer(@RequestBody TransferRequest transferRequest) throws TransferServiceException, NotFoundException {
        transferService.transfer(transferRequest.getFromAccount(), transferRequest.getToAccount(), transferRequest.getAmount());
        return true;
    }
}