package com.zeta_training.restaurant_management_system.repository;

import com.zeta_training.restaurant_management_system.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByTableBookingId(Long tableBookingId);
}
