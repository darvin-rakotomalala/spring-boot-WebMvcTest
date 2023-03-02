package com.poc.service.application;

import com.poc.model.dto.PaymentRequest;
import com.poc.model.dto.PaymentResponse;
import com.poc.model.dto.ReceiptDTO;

public interface OrderSA {
    PaymentResponse pay(Long orderId, PaymentRequest paymentRequest);
    ReceiptDTO getReceipt(Long orderId);
}
