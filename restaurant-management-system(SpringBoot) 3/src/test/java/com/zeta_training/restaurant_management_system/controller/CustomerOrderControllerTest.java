package com.zeta_training.restaurant_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeta_training.restaurant_management_system.dto.OrderItemRequest;
import com.zeta_training.restaurant_management_system.dto.OrderRequest;
import com.zeta_training.restaurant_management_system.service.CustomerOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class CustomerOrderControllerTest {
    @Mock
    private CustomerOrderService customerOrderService;

    private CustomerOrderController customerOrderController;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();
        customerOrderController = new CustomerOrderController(customerOrderService);
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(customerOrderController).build();
    }

    @Test
    public void testPlaceOrder_success() throws Exception {
        OrderItemRequest orderItemRequest1 = OrderItemRequest.builder()
                .menuItemId(1L)
                .quantity(2L)
                .build();
        OrderItemRequest orderItemRequest2 = OrderItemRequest.builder()
                .menuItemId(2L)
                .quantity(1L)
                .build();
        OrderRequest orderRequest = OrderRequest.builder()
                .tableBookingId(1L)
                .items(List.of(orderItemRequest1, orderItemRequest2))
                .build();

        MockHttpServletRequestBuilder mockRequest = post("/orders/place").
                contentType("application/json").content(objectMapper.writeValueAsString(orderRequest));

        mockMvc.perform(mockRequest).andExpect(status().isOk());

         Mockito.verify(customerOrderService).placeOrUpdateOrder(Mockito.any(OrderRequest.class));
    }
}
