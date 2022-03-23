package com.themusicstore.msgqueue.business.services;

import com.themusicstore.msgqueue.persistence.model.Discount;
import com.themusicstore.msgqueue.persistence.repository.DiscountRepository;
import com.themusicstore.msgqueue.web.dto.VinylDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, RestTemplate restTemplate) {
        this.discountRepository = discountRepository;
        this.restTemplate = restTemplate;
    }

    public List<Discount> getAll() {
        return discountRepository.findAll();
    }

    public Discount getByVinylId(Long id) {
        return discountRepository.findByVinylId(id);
    }

    public Discount saveDiscount(Discount discount) {
        log.info("Inside saveDiscount method of DiscountService");
        VinylDto vinylDto = restTemplate.getForObject(
                "http://app:8080/api/vinyls/" + discount.getVinylId(), VinylDto.class);
        assert vinylDto != null;
        if (Objects.equals(vinylDto.getId(), discount.getVinylId())) {
            if (vinylDto.getStock() > 0) {
                return discountRepository.save(discount);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe stock desse vinyl na loja");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O vinyl não tem desconto");
        }
    }

    public boolean verifyIfVinylHasDiscount(Long vinylId) {
        Discount discount = discountRepository.findByVinylId(vinylId);
        VinylDto vinylDto = restTemplate.getForObject(
                "http://app:8080/api/vinyls/" + discount.getVinylId(), VinylDto.class);
        assert vinylDto != null;
        return Objects.equals(discount.getVinylId(), vinylDto.getId());
    }
}
