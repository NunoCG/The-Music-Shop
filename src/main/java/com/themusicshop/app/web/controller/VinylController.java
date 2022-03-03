package com.themusicshop.app.web.controller;

import com.themusicshop.app.business.services.VinylService;
import com.themusicshop.app.persistence.model.Vinyl;
import com.themusicshop.app.web.dto.VinylDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/vinyls")
public class VinylController {

    private final ModelMapper modelMapper;
    private final VinylService vinylService;

    @Autowired
    public VinylController(ModelMapper modelMapper, VinylService vinylService) {
        this.modelMapper = modelMapper;
        this.vinylService = vinylService;
    }

    @GetMapping()
    public List<VinylDto> getVinyl() {
        return modelMapper.map(vinylService.getVinyls(), List.class);
    }

    @GetMapping("/{id}")
    public VinylDto getVinylById(@PathVariable("id") Long id) {
        return modelMapper.map(vinylService.getVinylById(id), VinylDto.class);
    }

    @PostMapping()
    public VinylDto saveVinyl(@RequestBody(required = false) VinylDto vinylDto) {
        vinylDto.setEntranceDate(LocalDate.now());
        Vinyl vinyl = modelMapper.map(vinylDto, Vinyl.class);
        vinyl = vinylService.saveVinyl(vinyl);
        return modelMapper.map(vinyl, VinylDto.class);
    }

    @PutMapping("/{id}")
    public VinylDto updateVinyl(@PathVariable("id") Long id, @RequestBody(required = false) VinylDto vinylDto) {
        vinylDto.setId(id);
        Vinyl vinyl = modelMapper.map(vinylDto, Vinyl.class);
        vinyl = vinylService.updateVinyl(vinyl);
        return modelMapper.map(vinyl, VinylDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteVinyl(@PathVariable("id") Long id) {
        vinylService.deleteVinyl(id);
    }
}
