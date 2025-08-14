package com.zeta_training.restaurant_management_system.repository;

import com.zeta_training.restaurant_management_system.entity.Order;
import com.zeta_training.restaurant_management_system.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
