package com.themusicshop.app.business.services;

import com.themusicshop.app.persistence.model.Client;
import com.themusicshop.app.persistence.model.Vinyl;
import com.themusicshop.app.persistence.repository.VinylRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VinylService {

    private final VinylRepository vinylRepository;

    @Autowired
    public VinylService(VinylRepository vinylRepository) {
        this.vinylRepository = vinylRepository;
    }

    public List<Vinyl> getVinyls() {
        return vinylRepository.findAll();
    }

    public Vinyl getVinylById(Long id) {
        Optional<Vinyl> vinylById = vinylRepository.findById(id);
        if (vinylById.isPresent()) {
            return vinylById.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe um vinyl com esse id");
        }
    }

    public Vinyl saveVinyl(Vinyl vinyl) {
        return vinylRepository.save(vinyl);
    }

    @Transactional
    public Vinyl updateVinyl(Vinyl vinylD) {
        Vinyl vinyl = vinylRepository.findById(vinylD.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O cliente com o id posto não existe"));

        if (vinylD.getComposerName() != null && vinylD.getComposerName().length() > 0 &&
                !Objects.equals(vinyl.getComposerName(), vinylD.getComposerName())) {
            vinyl.setComposerName(vinylD.getComposerName());
        }
        if (vinylD.getCategory() != null && vinylD.getCategory().length() > 0
                && !Objects.equals(vinyl.getCategory(), vinylD.getCategory())) {
            vinyl.setCategory(vinylD.getCategory());
        }
        if (vinylD.getMusicName() != null && vinylD.getMusicName().length() > 0 &&
                !Objects.equals(vinyl.getMusicName(), vinylD.getMusicName())) {
            vinyl.setMusicName(vinylD.getMusicName());
        }
        if (vinylD.getAlbum() != null && vinylD.getAlbum().length() > 0 && !Objects.equals(vinyl.getAlbum(), vinylD.getAlbum())) {
            vinyl.setAlbum(vinylD.getAlbum());
        }
        vinyl.setEntranceDate(LocalDate.now());
        if (vinylD.getPrice() != vinyl.getPrice() && vinylD.getPrice() >= 0) {
            vinyl.setPrice(vinylD.getPrice());
        }
        if (vinylD.getStock() != vinyl.getStock() && vinylD.getStock() >= 0) {
            vinyl.setStock(vinylD.getStock());
        }

        return vinyl;
    }

    public void deleteVinyl(Long id) {
        boolean vinylExists = vinylRepository.existsById(id);
        if (vinylExists) {
            vinylRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe um vinyl com esse id");
        }
    }
}
