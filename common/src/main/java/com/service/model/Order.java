package com.service.model;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Order {
    String orderId;
    double price;
}