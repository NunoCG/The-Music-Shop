package com.themusicstore.rental.business.services;

import com.themusicstore.rental.persistence.model.Rental;
import com.themusicstore.rental.persistence.repository.RentalRepository;
import com.themusicstore.rental.web.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public RentalService(RentalRepository rentalRepository, RestTemplate restTemplate) {
        this.rentalRepository = rentalRepository;
        this.restTemplate = restTemplate;
    }

    public List<Rental> getRentals() {
        log.info("Inside getRentals method of RentalService");
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        log.info("Inside getRentalById method of RentalService");
        return rentalRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O o id do aluguer não existe"));
    }

    public List<Rental> getRentalByClientId(Long clientId) {
        log.info("Inside getRentalByClientId of RentalService");
        return rentalRepository.findByClientId(clientId);
    }

    public double getVinylPrice(Long idVinyl) {
        VinylDto vinylDto = restTemplate.getForObject("http://app:8080/api/vinyls/" + idVinyl, VinylDto.class);
        assert vinylDto != null;
        if (Objects.equals(idVinyl, vinylDto.getId())) {
            return vinylDto.getPrice();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "O id do vinyl que foi inserido não existe, impossivel devolver o preço");
        }
    }

    public Rental saveRental(Rental rental) {
        log.info("Inside saveRental method of RentalService");
        ClientDto clientDto = restTemplate.getForObject(
                "http://app:8080/api/clients/" + rental.getClientId(), ClientDto.class);
        VinylDto vinylDto = restTemplate.getForObject(
                "http://app:8080/api/vinyls/" + rental.getVinylId(), VinylDto.class);
        assert clientDto != null;
        assert vinylDto != null;
        if (Objects.equals(rental.getClientId(), clientDto.getId()) &&
                Objects.equals(rental.getVinylId(), vinylDto.getId())) {
            if (rental.getPrice() < clientDto.getBalance()) {
                if (vinylDto.getStock() > 0) {
                    TransactionDto transactionDto = new TransactionDto();
                    transactionDto.setTransactionType(2);
                    transactionDto.setClientId(clientDto.getId());
                    transactionDto.setAmount(rental.getPrice());
                    restTemplate.postForObject(
                            "http://app:8080/api/clients/transactions",transactionDto, TransactionDto.class);
                    restTemplate.put("http://app:8080/api/vinyls/stock/" + vinylDto.getId(), VinylDto.class);
                    return rentalRepository.save(rental);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Não existe stock suficiente do vinyl escolhido");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Não tem saldo suficiente para alugar o vinyl");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "O id do cliente ou o id do vinyl que inseriu não existe");
        }
    }

    public ClientRentalDto getClientRental(Long rentalId) {
        ClientRentalDto clientRentalDto = new ClientRentalDto();
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluguer não foi encontrado"));

        ClientDto clientDto = restTemplate.getForObject(
                "http://app:8080/api/clients/" + rental.getClientId(), ClientDto.class);
        VinylDto vinylDto = restTemplate.getForObject(
                "http://app:8080/api/vinyls/" + rental.getVinylId(), VinylDto.class);
        clientRentalDto.setRental(rental);
        clientRentalDto.setClientDto(clientDto);
        clientRentalDto.setVinylDto(vinylDto);

        return clientRentalDto;
    }
}
