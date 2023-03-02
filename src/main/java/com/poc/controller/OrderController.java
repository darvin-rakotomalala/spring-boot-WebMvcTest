package com.poc.controller;

import com.poc.model.dto.PaymentRequest;
import com.poc.model.dto.PaymentResponse;
import com.poc.model.dto.ReceiptDTO;
import com.poc.service.application.OrderSA;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderSA orderSA;

    @PostMapping("/{id}/payment")
    public PaymentResponse pay(
            @PathVariable("id") Long orderId,
            @RequestBody PaymentRequest paymentRequest) {
        return orderSA.pay(orderId, paymentRequest);
    }

    @GetMapping("/{id}/receipt")
    public ReceiptDTO getReceipt(@PathVariable("id") Long orderId) {
        return orderSA.getReceipt(orderId);
    }

}
