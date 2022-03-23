package com.themusicstore.app.business.services;

import com.themusicstore.app.persistence.model.Vinyl;
import com.themusicstore.app.persistence.repository.VinylRepository;
import com.themusicstore.app.web.dto.DiscountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class VinylService {

    private final VinylRepository vinylRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public VinylService(VinylRepository vinylRepository, RestTemplate restTemplate) {
        this.vinylRepository = vinylRepository;
        this.restTemplate = restTemplate;
    }

    public List<Vinyl> getVinyls() {
        log.info("Inside getVinyls method of VinylService");
        return vinylRepository.findAll();
    }

    public Vinyl getVinylById(Long id) {
        log.info("Inside getVinylById method of VinylService");
        Optional<Vinyl> vinylById = vinylRepository.findById(id);
        if (vinylById.isPresent()) {
            return vinylById.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe um vinyl com esse id");
        }
    }

    public List<Vinyl> getVinylsByMusicNameOrAlbum(String musicName, String album) {
        log.info("Inside getVinylsByMusicNameOrAlbum method of VinylService");
        return vinylRepository.findVinylsByMusicNameOrAlbum(musicName, album);
    }

    public Vinyl saveVinyl(Vinyl vinyl) {
        log.info("Inside saveVinyl method of VinylService");
        return vinylRepository.save(vinyl);
    }

    @Transactional
    public Vinyl updateVinyl(Vinyl vinylD) {
        log.info("Inside updateVinyl method of VinylService");
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
        log.info("Inside deleteVinyl method of VinylService");
        boolean vinylExists = vinylRepository.existsById(id);
        if (vinylExists) {
            vinylRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe um vinyl com esse id");
        }
    }

    public void updateVinylStock(Long id) {
        Vinyl vinyl = vinylRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O id do vinyl inserido não existe"));
        int newStock = vinyl.getStock() - 1;
        vinyl.setStock(newStock);
        vinylRepository.save(vinyl);
    }

    public void updateVinylPrice(Long vinylId) {
        Vinyl vinyl = vinylRepository.findById(vinylId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O id do vinyl inserido não existe"));
        DiscountDto discountDto = restTemplate.getForObject(
          "http://msgqueue-app:8080/api/discounts/" + vinyl.getId(), DiscountDto.class);
        assert discountDto != null;
        if (Objects.equals(vinyl.getId(), discountDto.getVinylId())) {
            double discount = discountDto.getDiscount() / 100;
            double discountValue = (vinyl.getPrice() * discount);
            double newPrice = vinyl.getPrice() - discountValue;
            vinyl.setPrice(newPrice);
            vinylRepository.save(vinyl);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
              "Não existe desconto no vinyl que introduziu");
        }
    }
}
