package com.poc.service.business;

import com.poc.model.domain.Payment;

public interface OrderSM {
    Payment pay(Long orderId, String creditCardNumber);
    Payment getByOrderId(Long orderId);
}
