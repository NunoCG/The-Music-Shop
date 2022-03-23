package com.themusicstore.app.web.dto;

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
    private String password;
    private double balance;
}
