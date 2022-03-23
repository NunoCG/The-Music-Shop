package com.themusicstore.app.web.dto;

import com.themusicstore.app.persistence.model.Client;
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
