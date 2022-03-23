package com.themusicstore.app.web.controller;

import com.themusicstore.app.business.services.TransactionService;
import com.themusicstore.app.persistence.model.Transaction;
import com.themusicstore.app.web.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/clients/transactions")
@Slf4j
public class TransactionController {

    private final ModelMapper modelMapper;
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(ModelMapper modelMapper, TransactionService transactionService) {
        this.modelMapper = modelMapper;
        this.transactionService = transactionService;
    }

    @GetMapping()
    public List<TransactionDto> getTransactions() {
        log.info("Inside getTransactions method of TransactionController");
        return modelMapper.map(transactionService.getTransactions(), List.class);
    }

    @PostMapping()
    public TransactionDto saveTransaction(@RequestBody TransactionDto transactionDto) {
        log.info("Inside saveTransaction method of TransactionController");
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        transaction = transactionService.depositOrWithdrawal(transaction);
        return modelMapper.map(transaction, TransactionDto.class);
    }


}
