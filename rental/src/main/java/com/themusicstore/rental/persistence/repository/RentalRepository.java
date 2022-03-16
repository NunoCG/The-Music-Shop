package com.themusicstore.rental.persistence.repository;

import com.themusicstore.rental.persistence.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("FROM Rental WHERE clientId = ?1")
    List<Rental> findByClientId(Long clientId);
}
