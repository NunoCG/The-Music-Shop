package com.themusicshop.app.web.service;

import com.themusicshop.app.business.services.VinylService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class DiscountConsumer {

    private final VinylService vinylService;

    public DiscountConsumer(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @JmsListener(destination = "discount-queue")
    public void listener(Long vinylId) {
        System.out.println(vinylId);
        vinylService.updateVinylPrice(vinylId);
    }
}
