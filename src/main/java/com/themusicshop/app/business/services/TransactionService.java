package com.themusicshop.app.business.services;

import com.themusicshop.app.persistence.model.Client;
import com.themusicshop.app.persistence.model.Transaction;
import com.themusicshop.app.persistence.repository.ClientRepository;
import com.themusicshop.app.persistence.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ClientRepository clientRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction deposit(Transaction transaction) {
        Client client = clientRepository.findById(transaction.getClientId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O cliente inserido não existe"));

        if (transaction.getTransactionType() == 1) {
            if (transaction.getAmount() > 0) {
                transactionRepository.save(transaction);
                double newBalance = client.getBalance() + transaction.getAmount();
                client.setBalance(newBalance);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do depósito tem de ser maior que 0");
            }
        }

        return transaction;
    }
}
