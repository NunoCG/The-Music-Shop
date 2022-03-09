package com.themusicshop.app.persistence.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Vinyl {

    @Id
    @SequenceGenerator(
            name = "vinyl_sequence",
            sequenceName = "vinyl_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vinyl_sequence"
    )
    private Long id;
    private String composerName;
    private String category;
    private String musicName;
    private String album;
    private LocalDate entranceDate;
    private double price;
    private int stock;
}
