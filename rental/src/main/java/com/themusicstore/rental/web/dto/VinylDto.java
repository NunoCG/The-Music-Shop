package com.themusicstore.rental.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VinylDto {

    private Long id;
    private String composerName;
    private String category;
    private String musicName;
    private String album;
    private LocalDate entranceDate;
    private double price;
    private int stock;
}
