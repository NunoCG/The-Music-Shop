package com.themusicshop.app.web.controller;

import com.themusicshop.app.business.services.ClientService;
import com.themusicshop.app.persistence.model.Client;
import com.themusicshop.app.web.dto.ClientDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/clients")
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
        return modelMapper.map(clientService.getClient(), List.class);
    }

    @GetMapping("/{id}")
    public ClientDto getClientById(@PathVariable Long id) {
        return modelMapper.map(clientService.getClientByID(id), ClientDto.class);
    }

    @PostMapping()
    public ClientDto saveClient(@RequestBody(required = false) ClientDto clientDto) {
        clientDto.setBalance(0);
        Client client = modelMapper.map(clientDto, Client.class);
        client = clientService.saveClient(client);
        return modelMapper.map(client, ClientDto.class);
    }

    @PutMapping("/{id}")
    public ClientDto updateClient(@PathVariable("id") Long id, @RequestBody(required = false) ClientDto clientDto) {
        clientDto.setId(id);
        Client client = modelMapper.map(clientDto, Client.class);
        client = clientService.updateClient(client);
        return modelMapper.map(client, ClientDto.class);
    }

    /*@PutMapping("/{id}/balance/{cash}")
    public double depositBalance(@PathVariable("id") Long id, @PathVariable("cash") double cash) {
        return clientService.deposit(id, cash);
    }*/
}
