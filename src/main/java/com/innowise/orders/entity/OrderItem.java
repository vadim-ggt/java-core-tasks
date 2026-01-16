package com.innowise.orders.entity;

import lombok.*;

@Data
@AllArgsConstructor
public class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;
}
