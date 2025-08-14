package com.zeta_training.restaurant_management_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_id_seq")
    @SequenceGenerator(name = "billing_id_seq", sequenceName = "billing_id_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    private Double totalAmount;

    private LocalDateTime generatedAt;
}
