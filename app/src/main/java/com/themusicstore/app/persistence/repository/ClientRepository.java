package com.themusicstore.app.persistence.repository;

import com.themusicstore.app.persistence.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Same as : SELECT * FROM Client WHERE email = ?
    Optional<Client> findClientByEmail(String email);
}
