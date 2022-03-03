package com.themusicshop.app.persistence.repository;

import com.themusicshop.app.persistence.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByEmail(String email);
}
