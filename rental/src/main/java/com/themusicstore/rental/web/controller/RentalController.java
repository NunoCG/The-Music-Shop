package com.themusicstore.rental.web.controller;

import com.themusicstore.rental.business.services.RentalService;
import com.themusicstore.rental.persistence.model.Rental;
import com.themusicstore.rental.web.dto.ClientRentalDto;
import com.themusicstore.rental.web.dto.RentalDto;
import com.themusicstore.rental.web.dto.VinylDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/rental")
@Slf4j
public class RentalController {

    private final ModelMapper modelMapper;
    private final RentalService rentalService;

    @Autowired
    public RentalController(ModelMapper modelMapper, RentalService rentalService) {
        this.modelMapper = modelMapper;
        this.rentalService = rentalService;
    }

    @GetMapping()
    public List<RentalDto> getRentals() {
        log.info("Inside getRentals of RentalController");
        return modelMapper.map(rentalService.getRentals(), List.class);
    }

    @GetMapping("/{id}")
    public List<RentalDto> getRentalById(@PathVariable Long id) {
        return modelMapper.map(rentalService.getRentalById(id), List.class);
    }

    @GetMapping("/client/{id}")
    public List<RentalDto> getRentalByClientId(@PathVariable("id") Long id) {
        return modelMapper.map(rentalService.getRentalByClientId(id), List.class);
    }

    @PostMapping()
    public RentalDto saveRental(@RequestBody(required = false) RentalDto rentalDto) {
        log.info("Inside saveRental of RentalController");
        rentalDto.setStartDate(LocalDate.now());
        rentalDto.setPrice(rentalService.getVinylPrice(rentalDto.getVinylId()));
        Rental rental = modelMapper.map(rentalDto, Rental.class);
        rental = rentalService.saveRental(rental);
        return modelMapper.map(rental, RentalDto.class);
    }

    @GetMapping("/clients/{id}")
    public ClientRentalDto getClientRental(@PathVariable("id") Long rentalId) {
        log.info("Inside getClientRental of RentalController");
        return rentalService.getClientRental(rentalId);
    }
}
