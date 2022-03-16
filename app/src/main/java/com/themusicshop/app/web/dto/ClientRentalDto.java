package com.themusicshop.app.web.dto;

import com.themusicshop.app.persistence.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRentalDto {

    private Client client;
    private RentalDto rentalDto;
}
