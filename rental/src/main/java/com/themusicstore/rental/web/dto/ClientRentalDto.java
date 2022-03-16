package com.themusicstore.rental.web.dto;

import com.themusicstore.rental.persistence.model.Rental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRentalDto {

    private Rental rental;
    private ClientDto clientDto;
    private VinylDto vinylDto;
}
