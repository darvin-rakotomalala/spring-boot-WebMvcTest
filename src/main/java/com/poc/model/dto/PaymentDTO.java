package com.poc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private OrderDTO order;
    private String creditCardNumber;
}
