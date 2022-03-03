package com.themusicshop.app.persistence.repository;

import com.themusicshop.app.persistence.model.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VinylRepository extends JpaRepository<Vinyl, Long> {

}