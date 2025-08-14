package com.zeta_training.restaurant_management_system.repository;

import com.zeta_training.restaurant_management_system.entity.TableBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableBookingRepository extends JpaRepository<TableBooking, Long> {
}
