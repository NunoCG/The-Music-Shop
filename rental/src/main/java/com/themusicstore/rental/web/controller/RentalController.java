package com.themusicstore.rental.web.controller;

import com.themusicstore.rental.business.services.RentalService;
import com.themusicstore.rental.persistence.model.Rental;
import com.themusicstore.rental.web.dto.RentalDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/rental")
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
        return modelMapper.map(rentalService.getRentals(), List.class);
    }

    @PostMapping()
    public RentalDto saveRental(@RequestBody(required = false) RentalDto rentalDto) {
        rentalDto.setStartDate(LocalDate.now());
        Rental rental = modelMapper.map(rentalDto, Rental.class);
        rental = rentalService.saveRental(rental);
        return modelMapper.map(rental, RentalDto.class);
    }
}
