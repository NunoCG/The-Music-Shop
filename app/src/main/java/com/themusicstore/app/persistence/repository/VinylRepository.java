package com.themusicstore.app.persistence.repository;

import com.themusicstore.app.persistence.model.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VinylRepository extends JpaRepository<Vinyl, Long> {

    @Query("FROM Vinyl WHERE musicName = ?1 OR album = ?2")
    List<Vinyl> findVinylsByMusicNameOrAlbum(String musicName, String album);
}