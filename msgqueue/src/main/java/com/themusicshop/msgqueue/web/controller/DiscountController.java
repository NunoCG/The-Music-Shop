package com.themusicshop.msgqueue.web.controller;

import com.themusicshop.msgqueue.business.services.DiscountService;
import com.themusicshop.msgqueue.persistence.model.Discount;
import com.themusicshop.msgqueue.web.dto.DiscountDto;
import com.themusicshop.msgqueue.web.service.DiscountPublisher;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/discounts")
@Slf4j
public class DiscountController {

    private final DiscountPublisher discountPublisher;
    private final ModelMapper modelMapper;
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountPublisher discountPublisher, ModelMapper modelMapper, DiscountService discountService) {
        this.discountPublisher = discountPublisher;
        this.modelMapper = modelMapper;
        this.discountService = discountService;
    }

    @GetMapping("/send/{id}")
    public void sendDiscount(@PathVariable("id") Long id) {
        log.info("Inside sendDiscount method of DiscountController");
        Discount discount = discountService.getByVinylId(id);
        if (discountService.verifyIfVinylHasDiscount(discount.getVinylId())) {
            discountPublisher.sendMessage(discount.getVinylId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe desconto para esse Vinyl");
        }
    }

    @GetMapping
    public List<DiscountDto> getAll() {
        log.info("Inside getAll method of DiscountController");
        return modelMapper.map(discountService.getAll(), List.class);
    }

    @GetMapping("/{id}")
    public DiscountDto getByVinylId(@PathVariable("id") Long id) {
        log.info("Inside getById method of DiscountController");
        return modelMapper.map(discountService.getByVinylId(id), DiscountDto.class);
    }

    @PostMapping
    public DiscountDto saveDiscount(@RequestBody(required = false) DiscountDto discountDto) {
        log.info("Inside saveDiscount method of DiscountController");
        Discount discount = modelMapper.map(discountDto, Discount.class);
        discount = discountService.saveDiscount(discount);
        return modelMapper.map(discountService.saveDiscount(discount), DiscountDto.class);
    }
}
