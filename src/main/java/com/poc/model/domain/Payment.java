package com.poc.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Order order;
    private String creditCardNumber;

    public Payment(Order order, String creditCardNumber) {
        this.order = order;
        this.creditCardNumber = creditCardNumber;
    }
}
