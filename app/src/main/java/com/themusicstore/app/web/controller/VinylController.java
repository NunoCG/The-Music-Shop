package com.themusicstore.app.web.controller;

import com.themusicstore.app.business.services.VinylService;
import com.themusicstore.app.persistence.model.Vinyl;
import com.themusicstore.app.web.dto.VinylDto;
import com.themusicstore.app.web.service.MsgQueueConsumer;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/vinyls")
@Slf4j
public class VinylController {

    private final ModelMapper modelMapper;
    private final VinylService vinylService;
    private final MsgQueueConsumer discountConsumer;

    @Autowired
    public VinylController(ModelMapper modelMapper, VinylService vinylService, MsgQueueConsumer discountConsumer) {
        this.modelMapper = modelMapper;
        this.vinylService = vinylService;
        this.discountConsumer = discountConsumer;
    }

    @GetMapping()
    public List<VinylDto> getVinyls() {
        log.info("Inside getVinyls method of VinylController");
        return modelMapper.map(vinylService.getVinyls(), List.class);
    }

    @GetMapping("/{id}")
    public VinylDto getVinylById(@PathVariable("id") Long id) {
        log.info("Inside getVinylById method of VinylController");
        return modelMapper.map(vinylService.getVinylById(id), VinylDto.class);
    }

    @GetMapping("/search")
    public List<VinylDto> getVinylsByMusicNameOrAlbum(@RequestParam(required = false) String musicName,
                                                      @RequestParam(required = false) String album) {
        log.info("Inside getVinylsByMusicNameOrAlbum method of VinylController");
        return modelMapper.map(vinylService.getVinylsByMusicNameOrAlbum(musicName, album), List.class);
    }

    @PostMapping()
    public VinylDto saveVinyl(@RequestBody(required = false) VinylDto vinylDto) {
        log.info("Inside saveVinyl method of VinylController");
        vinylDto.setEntranceDate(LocalDate.now());
        Vinyl vinyl = modelMapper.map(vinylDto, Vinyl.class);
        vinyl = vinylService.saveVinyl(vinyl);
        return modelMapper.map(vinyl, VinylDto.class);
    }

    @PutMapping("/{id}")
    public VinylDto updateVinyl(@PathVariable("id") Long id, @RequestBody(required = false) VinylDto vinylDto) {
        log.info("Inside updateVinyl method of VinylController");
        vinylDto.setId(id);
        Vinyl vinyl = modelMapper.map(vinylDto, Vinyl.class);
        vinyl = vinylService.updateVinyl(vinyl);
        return modelMapper.map(vinyl, VinylDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteVinyl(@PathVariable("id") Long id) {
        log.info("Inside deleteVinyl method of VinylController");
        vinylService.deleteVinyl(id);
    }

    @PutMapping("/stock/{id}")
    public void updateVinylStock(@PathVariable("id") Long id) {
        log.info("Inside updateVinylStock method of VinylController");
        vinylService.updateVinylStock(id);
    }

    @PutMapping("/price/{id}")
    public void updateVinylPrice(@PathVariable("id") Long id) {
        log.info("Inside updateVinylPrice method of VinylController");
        discountConsumer.listener(id);
    }
}
