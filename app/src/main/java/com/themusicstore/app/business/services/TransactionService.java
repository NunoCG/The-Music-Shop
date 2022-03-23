package com.themusicstore.app.business.services;

import com.themusicstore.app.persistence.model.Client;
import com.themusicstore.app.persistence.model.Transaction;
import com.themusicstore.app.persistence.repository.ClientRepository;
import com.themusicstore.app.persistence.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ClientRepository clientRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
    }

    public List<Transaction> getTransactions() {
        log.info("Inside getTransactions method of TransactionService");
        return transactionRepository.findAll();
    }

    public Transaction depositOrWithdrawal(Transaction transaction) {
        log.info("Inside depositOrWithdrawal method of TransactionService");
        Client client = clientRepository.findById(transaction.getClientId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O cliente inserido não existe"));

        if (transaction.getTransactionType() == 1) {
            if (transaction.getAmount() > 0) {
                transactionRepository.save(transaction);
                double newBalance = client.getBalance() + transaction.getAmount();
                client.setBalance(newBalance);
                clientRepository.save(client);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "O valor do depósito tem de ser maior que 0");
            }
        } else if (transaction.getTransactionType() == 2) {
            if (transaction.getAmount() <= client.getBalance()) {
                transactionRepository.save(transaction);
                double newBalance = client.getBalance() - transaction.getAmount();
                client.setBalance(newBalance);
                clientRepository.save(client);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "O valor de levantamento tem de ser menor ou igual ao saldo do cliente");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O tipo de transação de tem ser 1 para depósito e 2 para levantamento");
        }

        return transaction;
    }
}
