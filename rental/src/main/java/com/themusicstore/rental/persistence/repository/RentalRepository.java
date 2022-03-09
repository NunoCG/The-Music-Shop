package com.themusicstore.rental.persistence.repository;

import com.themusicstore.rental.persistence.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}
