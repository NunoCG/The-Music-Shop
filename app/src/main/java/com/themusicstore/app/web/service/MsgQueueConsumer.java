package com.themusicstore.app.web.service;

import com.themusicstore.app.business.services.VinylService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MsgQueueConsumer {

    private final VinylService vinylService;

    public MsgQueueConsumer(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @JmsListener(destination = "msqueue-discount-queue")
    public void listener(Long vinylId) {
        log.info("Inside listener method of MsgQueueConsumer");
        System.out.println(vinylId);
        vinylService.updateVinylPrice(vinylId);
    }
}
