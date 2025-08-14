package com.zeta_training.restaurant_management_system.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private String menuItemName;
    private Long quantity;
    private Double pricePerUnit;
    private Double totalPrice;
}
