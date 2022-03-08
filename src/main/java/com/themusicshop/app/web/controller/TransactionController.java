package com.themusicshop.app.web.controller;

import com.themusicshop.app.business.services.TransactionService;
import com.themusicshop.app.persistence.model.Transaction;
import com.themusicshop.app.web.dto.TransactionDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/clients/transactions")
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
        return modelMapper.map(transactionService.getTransactions(), List.class);
    }

    @PutMapping()
    public TransactionDto saveTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        transaction = transactionService.deposit(transaction);
        return modelMapper.map(transaction, TransactionDto.class);
    }
}
