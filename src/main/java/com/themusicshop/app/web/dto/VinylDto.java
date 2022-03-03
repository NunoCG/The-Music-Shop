package com.themusicshop.app.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
