package com.poc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private Long orderId;
    private String creditCardNumber;
}
