package com.poc;

import com.poc.controller.OrderController;
import com.poc.exception.OrderAlreadyPaid;
import com.poc.mapper.PaymentMapper;
import com.poc.model.domain.Order;
import com.poc.model.domain.Payment;
import com.poc.model.dto.ReceiptDTO;
import com.poc.service.application.OrderSA;
import com.poc.service.application.OrderSAImpl;
import com.poc.service.business.OrderSM;
import com.poc.service.business.OrderSMImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @MockBean
    private OrderSMImpl orderSMImpl;

    @MockBean
    private OrderSAImpl orderSAImpl;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void payOrder() throws Exception {
        Order order = new Order(1L, LocalDateTime.now(), 100.0, false);
        Payment payment = new Payment(1000L, order, "4532756279624064");

        when(orderSMImpl.pay(eq(1L), eq("4532756279624064"))).thenReturn(payment);

        mockMvc.perform(post("/orders/{id}/payment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"creditCardNumber\": \"4532756279624064\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void paymentFailsWhenOrderIsNotFound() throws Exception {
        when(orderSMImpl.pay(eq(1L), any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(post("/orders/{id}/payment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"creditCardNumber\": \"4532756279624064\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void paymentFailsWhenCreditCardNumberNotGiven() throws Exception {
        mockMvc.perform(post("/orders/{id}/payment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void cannotPayAlreadyPaidOrder() throws Exception {
        when(orderSMImpl.pay(eq(1L), any())).thenThrow(OrderAlreadyPaid.class);

        mockMvc.perform(post("/orders/{id}/payment", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"creditCardNumber\": \"4532756279624064\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void receiptCanBeFound() throws Exception {
        ReceiptDTO receipt = new ReceiptDTO(LocalDateTime.now(), "4532756279624064", 100.0);

        when(orderSAImpl.getReceipt(eq(1L))).thenReturn(receipt);

        mockMvc.perform(get("/orders/{id}/receipt", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getReceiptForOrder() throws Exception {
        ReceiptDTO receipt = new ReceiptDTO(
                LocalDateTime.now(),
                "4532756279624064",
                100.0);

        when(orderSAImpl.getReceipt(eq(1L))).thenReturn(receipt);

        mockMvc.perform(get("/orders/{id}/receipt", 1L))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.creditCardNumber").value("4532756279624064"))
                .andExpect(jsonPath("$.amount").value(100.0));
    }

}
