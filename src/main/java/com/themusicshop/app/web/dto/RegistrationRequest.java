package com.themusicshop.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String name;
    private final Date birthDate;
    private final String gender;
    private final String email;
    private final String password;
    private final double balance;
}
