package com.themusicshop.app.persistence.repository;

import com.themusicshop.app.persistence.model.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VinylRepository extends JpaRepository<Vinyl, Long> {

    List<Vinyl> findByMusicName(String musicName);
    List<Vinyl> findByAlbum(String album);
}