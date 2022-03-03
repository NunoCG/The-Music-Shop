package com.themusicshop.app.business.services;

import com.themusicshop.app.persistence.model.Client;
import com.themusicshop.app.web.dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final ClientService clientService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }
        return clientService.signUpClient(new Client(
                request.getName(),
                request.getBirthDate(),
                request.getGender(),
                request.getEmail(),
                request.getPassword(),
                request.getBalance()
                )
        );
    }
}
