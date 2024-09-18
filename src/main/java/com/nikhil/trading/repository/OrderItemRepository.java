package com.nikhil.trading.repository;

import com.nikhil.trading.modal.Order;
import com.nikhil.trading.modal.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
