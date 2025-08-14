package com.zeta_training.restaurant_management_system.service;

import com.zeta_training.restaurant_management_system.dto.BillResponseDTO;
import com.zeta_training.restaurant_management_system.dto.OrderItemRequest;
import com.zeta_training.restaurant_management_system.dto.OrderRequest;
import com.zeta_training.restaurant_management_system.entity.BillRecord;
import com.zeta_training.restaurant_management_system.entity.MenuItem;
import com.zeta_training.restaurant_management_system.entity.Order;
import com.zeta_training.restaurant_management_system.entity.OrderItem;
import com.zeta_training.restaurant_management_system.entity.TableBooking;
import com.zeta_training.restaurant_management_system.enumeration.BookingStatus;
import com.zeta_training.restaurant_management_system.enumeration.OrderItemStatus;
import com.zeta_training.restaurant_management_system.enumeration.OrderStatus;
import com.zeta_training.restaurant_management_system.repository.BillRecordRepository;
import com.zeta_training.restaurant_management_system.repository.MenuItemRepository;
import com.zeta_training.restaurant_management_system.repository.OrderItemRepository;
import com.zeta_training.restaurant_management_system.repository.OrderRepository;
import com.zeta_training.restaurant_management_system.repository.TableBookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerOrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private TableBookingRepository tableBookingRepository;

    @Mock
    private BillRecordRepository billRecordRepository;

    private CustomerOrderService customerOrderService;

    @BeforeEach
    public void setUp() {
        customerOrderService = new CustomerOrderService(orderRepository, menuItemRepository, orderItemRepository, tableBookingRepository, billRecordRepository);
    }

    @Test
    void placeNewOrderSuccessfully() {
        Long bookingId = 1L;
        TableBooking booking = new TableBooking();
        booking.setId(bookingId);
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("Test Item");
        menuItem.setPrice(10.0);

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setMenuItemId(1L);
        itemRequest.setQuantity(2L);

        List<OrderItemRequest> itemRequests = List.of(itemRequest);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setTableBookingId(bookingId);
        orderRequest.setItems(itemRequests);

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(2L);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setTableBooking(booking);
        savedOrder.setOrderItems(new ArrayList<>(Collections.singletonList(orderItem)));
        savedOrder.setStatus(OrderStatus.PLACED);

        when(tableBookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(orderRepository.findByTableBookingId(bookingId)).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        Order result = customerOrderService.placeOrUpdateOrder(orderRequest);

        verify(tableBookingRepository).findById(bookingId);
        verify(orderRepository).findByTableBookingId(bookingId);
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(menuItemRepository).findById(1L);

        assertNotNull(result);
        assertEquals(OrderStatus.PLACED, result.getStatus());
        assertEquals(booking, result.getTableBooking());
    }
    @Test
    void updateOrderItemStatusSuccessfully() {
        Long orderItemId = 1L;
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemId);
        orderItem.setStatus(OrderItemStatus.PENDING);

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));

        customerOrderService.updateOrderItemStatus(orderItemId, OrderItemStatus.PREPARED);

        verify(orderItemRepository).findById(orderItemId);
        verify(orderItemRepository).save(orderItem);
        assertEquals(OrderItemStatus.PREPARED, orderItem.getStatus());
    }
    @Test
    void generateBillSuccessfully() {
        Long orderId = 1L;

        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("Pizza");
        menuItem.setPrice(15.0);

        TableBooking booking = new TableBooking();
        booking.setId(2L);
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(10L);
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(2L);
        orderItem.setStatus(OrderItemStatus.PREPARED);

        Order order = new Order();
        order.setId(orderId);
        order.setTableBooking(booking);
        order.setOrderItems(new ArrayList<>(List.of(orderItem)));
        order.setStatus(OrderStatus.PLACED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(tableBookingRepository.save(any(TableBooking.class))).thenReturn(booking);
        when(billRecordRepository.save(any(BillRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        BillResponseDTO response = customerOrderService.generateBill(orderId);

        verify(orderRepository).findById(orderId);
        verify(tableBookingRepository).save(booking);
        verify(billRecordRepository).save(any(BillRecord.class));
        verify(orderRepository).save(order);

        assertNotNull(response);
        assertEquals(orderId, response.getOrderId());
        assertEquals(30.0, response.getTotalAmount());
        assertEquals(1, response.getOrderItems().size());
        assertEquals("Pizza", response.getOrderItems().get(0).getMenuItemName());
    }
}
