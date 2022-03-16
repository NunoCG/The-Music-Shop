package com.themusicshop.msgqueue.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {

    private Long id;
    private Long vinylId;
    private double discount;
}
