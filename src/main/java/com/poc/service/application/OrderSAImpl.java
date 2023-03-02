package com.poc.service.application;

import com.poc.mapper.PaymentMapper;
import com.poc.model.dto.PaymentDTO;
import com.poc.model.dto.PaymentRequest;
import com.poc.model.dto.PaymentResponse;
import com.poc.model.dto.ReceiptDTO;
import com.poc.service.business.OrderSM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderSAImpl implements OrderSA {

    private final OrderSM orderSM;

    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse pay(Long orderId, PaymentRequest paymentRequest) {
        PaymentDTO payment = paymentMapper.toDTO(orderSM.pay(orderId, paymentRequest.getCreditCardNumber()));
        return new PaymentResponse(payment.getOrder().getId(), payment.getCreditCardNumber());
    }

    @Override
    public ReceiptDTO getReceipt(Long orderId) {
        PaymentDTO payment = paymentMapper.toDTO(orderSM.getByOrderId(orderId));
        return new ReceiptDTO(payment.getOrder().getDate(), payment.getCreditCardNumber(), payment.getOrder().getAmount());
    }

}
