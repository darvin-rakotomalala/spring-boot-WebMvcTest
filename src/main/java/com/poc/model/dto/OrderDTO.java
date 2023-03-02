package com.poc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime date;
    private Double amount;
    private Boolean paid;
}
