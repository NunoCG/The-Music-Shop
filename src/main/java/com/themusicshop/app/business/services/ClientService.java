package com.themusicshop.app.business.services;

import com.themusicshop.app.persistence.model.Client;
import com.themusicshop.app.persistence.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "Utilizador com o email %s não encontrado";
    @Autowired
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clientRepository.findClientByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public String signUpClient(Client client) {
        boolean userExists = clientRepository.findClientByEmail(client.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);

        clientRepository.save(client);

        // TODO: Send confirmation token
        return "It Works";
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

}
