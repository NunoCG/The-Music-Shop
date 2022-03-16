package com.themusicstore.rental.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ClientDto {

    private Long id;
    private String name;
    private Date birthDate;
    private String gender;
    private String email;
    private double balance;
}
