package com.themusicshop.app.business.services;

import com.themusicshop.app.persistence.model.Vinyl;
import com.themusicshop.app.persistence.repository.VinylRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@AllArgsConstructor
public class VinylService {

    @Autowired
    private final VinylRepository vinylRepository;

    public List<Vinyl> getAll() {
        return vinylRepository.findAll();
    }

    public Vinyl addVinyl(Vinyl vinyl) {
        return vinylRepository.save(vinyl);
    }
}
