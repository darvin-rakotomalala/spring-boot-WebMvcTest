package com.poc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {
    private LocalDateTime date;
    private String creditCardNumber;
    private Double amount;
}
