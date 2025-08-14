package com.zeta_training.restaurant_management_system.repository;

import com.zeta_training.restaurant_management_system.entity.User;
import com.zeta_training.restaurant_management_system.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User>findByEmail(String email);
    boolean existsByRole(Role role);
}
