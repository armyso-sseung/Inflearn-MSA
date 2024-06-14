package com.example.orderservice.vo;

import lombok.Data;

@Data
public class RequestOrder {
    private String userId;

    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
