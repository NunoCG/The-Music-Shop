package com.themusicshop.app.business.services;

import com.themusicshop.app.persistence.model.Client;
import com.themusicshop.app.persistence.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClient() {
        return clientRepository.findAll();
    }

    public Client getClientByID(Long id) {
        Optional<Client> clientById = clientRepository.findById(id);
        if (clientById.isPresent()) {
            return clientById.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe um cliente com esse id");
        }
    }

    public Client saveClient(Client client) {
        Optional<Client> clientByEmail = clientRepository.findClientByEmail(client.getEmail());
        if (clientByEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Email já existe");
        } else {
            return clientRepository.save(client);
        }
    }

    @Transactional
    public Client updateClient(Client clientDto) {
        Client client = clientRepository.findById(clientDto.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "O cliente com o id " + clientDto.getId() + " não existe"));

        if (clientDto.getName() != null && clientDto.getName().length() > 0 &&
                !Objects.equals(client.getName(), clientDto.getName())) {
            client.setName(clientDto.getName());
        }
        if (clientDto.getBirthDate() != null && !Objects.equals(client.getBirthDate(), clientDto.getBirthDate())) {
            client.setBirthDate(clientDto.getBirthDate());
        }
        if (clientDto.getGender() != null && clientDto.getGender().length() > 0 &&
                !Objects.equals(client.getGender(), clientDto.getGender())) {
            client.setGender(clientDto.getGender());
        }
        if (clientDto.getEmail() != null && clientDto.getEmail().length() > 0 &&
                !Objects.equals(client.getEmail(), clientDto.getEmail())) {
            Optional<Client> clientOptional = clientRepository.findClientByEmail(clientDto.getEmail());
            if (clientOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O email escolhido já está a ser usado");
            } else {
                client.setEmail(clientDto.getEmail());
            }
        }
        if (clientDto.getPassword() != null && clientDto.getPassword().length() > 0 &&
                !Objects.equals(client.getPassword(), clientDto.getPassword())) {
            client.setPassword(clientDto.getPassword());
        }

        return client;
    }

    /*public Client deposit(Client clientD, double cash) {
       Client client = clientRepository.findById(clientD.getId()).orElseThrow(() ->
               new ResponseStatusException(HttpStatus.NOT_FOUND,
                       "O cliente com o id " + clientD.getId() + " não existe"));
        if (cash > 0) {
            double newBalance = clientD.getBalance() + cash;
            clientD.setBalance(newBalance);
            client.setBalance(clientD.getBalance());
            return client;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O depósito tem de ser maior que 0");
        }
    }*/
}
