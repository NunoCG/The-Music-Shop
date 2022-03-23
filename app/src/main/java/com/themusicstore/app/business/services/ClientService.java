package com.themusicstore.app.business.services;

import com.themusicstore.app.persistence.model.Client;
import com.themusicstore.app.persistence.repository.ClientRepository;
import com.themusicstore.app.web.dto.RentalDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public ClientService(ClientRepository clientRepository, RestTemplate restTemplate) {
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
    }

    public List<Client> getClient() {
        log.info("Inside getClient method of ClientService");
        return clientRepository.findAll();
    }

    public Client getClientByID(Long id) {
        log.info("Inside getClientById method of ClientService");
        Optional<Client> clientById = clientRepository.findById(id);
        if (clientById.isPresent()) {
            return clientById.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe um cliente com esse id");
        }
    }

    public Client saveClient(Client client) {
        log.info("Inside saveClient method of ClientService");
        Optional<Client> clientByEmail = clientRepository.findClientByEmail(client.getEmail());
        if (clientByEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Email já existe");
        } else {
            return clientRepository.save(client);
        }
    }

    @Transactional
    public Client updateClient(Client clientDto) {
        log.info("Inside updateClient method of ClientService");
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

    public List<RentalDto> getClientRentals(Long clientId) {
        return restTemplate.getForObject(
                "http://rental-app:8080/api/rental/client/" + clientId, List.class);
    }
}
