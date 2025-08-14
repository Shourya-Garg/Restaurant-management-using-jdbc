package com.zeta_training.restaurant_management_system.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long tableBookingId;
    private List<OrderItemRequest> items;
}

