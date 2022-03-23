package com.themusicstore.app.web.controller;

import com.themusicstore.app.business.services.ClientService;
import com.themusicstore.app.persistence.model.Client;
import com.themusicstore.app.web.dto.ClientDto;
import com.themusicstore.app.web.dto.RentalDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/clients")
@Slf4j
public class ClientController {

    private final ModelMapper modelMapper;
    private final ClientService clientService;

    @Autowired
    public ClientController(ModelMapper modelMapper, ClientService clientService) {
        this.modelMapper = modelMapper;
        this.clientService = clientService;
    }

    @GetMapping()
    public List<ClientDto> getClient() {
        log.info("Inside getClient method of ClientController");
        return modelMapper.map(clientService.getClient(), List.class);
    }

    @GetMapping("/{id}")
    public ClientDto getClientById(@PathVariable Long id) {
        log.info("Inside getClientById method of ClientController");
        return modelMapper.map(clientService.getClientByID(id), ClientDto.class);
    }

    @PostMapping()
    public ClientDto saveClient(@RequestBody(required = false) ClientDto clientDto) {
        log.info("Inside saveClient method of ClientController");
        clientDto.setBalance(0);
        Client client = modelMapper.map(clientDto, Client.class);
        client = clientService.saveClient(client);
        return modelMapper.map(client, ClientDto.class);
    }

    @PutMapping("/{id}")
    public ClientDto updateClient(@PathVariable("id") Long id, @RequestBody(required = false) ClientDto clientDto) {
        log.info("Inside updateClient method of ClientController");
        clientDto.setId(id);
        Client client = modelMapper.map(clientDto, Client.class);
        client = clientService.updateClient(client);
        return modelMapper.map(client, ClientDto.class);
    }

    @GetMapping("/rental/{clientId}")
    public List<RentalDto> getClientRentals(@PathVariable("clientId") Long id) {
        log.info("Inside getClientRentals method of ClientController");
        return clientService.getClientRentals(id);
    }
}
