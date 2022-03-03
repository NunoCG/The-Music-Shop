package com.themusicshop.app.web.controller;

import com.themusicshop.app.business.services.VinylService;
import com.themusicshop.app.persistence.model.Vinyl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/vinyl")
@AllArgsConstructor
public class VinylController {

    @Autowired
    private final VinylService vinylService;

    @GetMapping("/all")
    public List<Vinyl> getAll() {
        return vinylService.getAll();
    }

    @PostMapping("/add")
    public Vinyl add(Vinyl vinyl) {
        return vinylService.addVinyl(vinyl);
    }
}
