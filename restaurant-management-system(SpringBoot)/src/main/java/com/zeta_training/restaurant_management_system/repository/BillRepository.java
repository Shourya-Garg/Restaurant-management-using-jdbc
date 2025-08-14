package com.zeta_training.restaurant_management_system.repository;

import com.zeta_training.restaurant_management_system.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

}
