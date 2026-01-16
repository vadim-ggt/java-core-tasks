package com.innowise.orders.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
    private int age;
    private String city;
}
