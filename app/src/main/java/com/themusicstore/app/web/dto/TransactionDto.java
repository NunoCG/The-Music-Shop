package com.themusicstore.app.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

    private Long id;
    private int transactionType;
    private double amount;
    private Long clientId;
}
