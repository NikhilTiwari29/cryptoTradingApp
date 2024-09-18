package com.nikhil.trading.request;

import com.nikhil.trading.enums.OrderType;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class CreateOrderRequest {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
