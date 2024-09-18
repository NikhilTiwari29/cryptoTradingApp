package com.nikhil.trading.service;

import com.nikhil.trading.enums.OrderType;
import com.nikhil.trading.modal.Coin;
import com.nikhil.trading.modal.Order;
import com.nikhil.trading.modal.OrderItem;
import com.nikhil.trading.modal.User;


import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId);

    List<Order> getAllOrdersForUser(Long userId, OrderType orderType,String assetSymbol);

    void cancelOrder(Long orderId);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}