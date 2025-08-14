package com.zeta_training.restaurant_management_system.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long menuItemId;
    private Long quantity;
}
