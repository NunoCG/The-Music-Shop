package com.themusicshop.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {

    private Long id;
    private Long vinylId;
    private Long clientId;
    private LocalDate startDate;
    private Date endDate;
    private double price;
}
