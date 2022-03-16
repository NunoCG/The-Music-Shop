package com.themusicshop.msgqueue.persistence.repository;

import com.themusicshop.msgqueue.persistence.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("FROM Discount WHERE vinylId = ?1")
    Discount findByVinylId(Long id);
}
