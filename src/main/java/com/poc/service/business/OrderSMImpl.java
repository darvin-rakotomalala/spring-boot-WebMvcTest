package com.poc.service.business;

import com.poc.exception.ErrorsEnum;
import com.poc.exception.FunctionalException;
import com.poc.model.domain.Order;
import com.poc.model.domain.Payment;
import com.poc.repository.OrderRepository;
import com.poc.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderSMImpl implements OrderSM {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Payment pay(Long orderId, String creditCardNumber) {
        try {
            log.info("----- pay");
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isEmpty()) {
                throw new FunctionalException(ErrorsEnum.ERR_MCS_ORDER_NOT_FOUND.getErrorMessage());
            }
            if (order.get().isPaid()) {
                throw new FunctionalException(ErrorsEnum.ERR_MCS_ORDER_IS_PAID.getErrorMessage());
            }
            orderRepository.save(order.get().markPaid());
            return paymentRepository.save(new Payment(order.get(), creditCardNumber));
        } catch (Exception e) {
            log.error("Error pay : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Payment getByOrderId(Long orderId) {
        try {
            log.info("----- getByOrderId : {}", orderId);
            Optional<Payment> payment = paymentRepository.findById(orderId);
            if (payment.isEmpty()) {
                throw new FunctionalException(ErrorsEnum.ERR_MCS_PAYMENT_NOT_FOUND.getErrorMessage());
            }
            return payment.get();
        } catch (Exception e) {
            log.error("Error getByOrderId : {} : {}", e.getMessage(), e);
            throw e;
        }
    }

}
