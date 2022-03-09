package com.themusicstore.rental.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class RentalDto {

    private Long id;
    private Long vinylId;
    private LocalDate startDate;
    private Date endDate;
}
