package com.zeta_training.restaurant_management_system.service;

import com.zeta_training.restaurant_management_system.dto.MenuItemDTO;
import com.zeta_training.restaurant_management_system.entity.MenuItem;
import com.zeta_training.restaurant_management_system.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MenuServiceTest {
    @Mock
    private MenuItemRepository menuItemRepository;

    private MenuService menuService;

    @BeforeEach
    public void setUp() {
        menuService = new MenuService(menuItemRepository);
    }

    @Test
    public void testAddMenuItem() {
        MenuItemDTO request = new MenuItemDTO();
        request.setName("Pizza");
        request.setPrice(10.99);

        menuService.addMenuItem(request);

        ArgumentCaptor<MenuItem> menuItemCaptor = ArgumentCaptor.forClass(MenuItem.class);
        verify(menuItemRepository).save(menuItemCaptor.capture());

        MenuItem savedMenuItem = menuItemCaptor.getValue();
        assertEquals("Pizza", savedMenuItem.getName());
        assertEquals(10.99, savedMenuItem.getPrice());
    }

    @Test
    public void testDeleteMenuItem_Exists() {
        Long id = 1L;
        MenuItem menuItem = MenuItem.builder()
                .id(id)
                .name("Pizza")
                .price(10.99)
                .build();
        when(menuItemRepository.findById(id)).thenReturn(Optional.of(menuItem));

        menuService.deleteMenuItem(id);

        verify(menuItemRepository).delete(menuItem);
    }

    @Test
    public void testDeleteMenuItem_NotFound() {
        Long id = 1L;
        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuService.deleteMenuItem(id);
        });

        assertEquals("Menu item not found", exception.getMessage());
        verify(menuItemRepository, never()).delete(any());
    }

    @Test
    public void testGetAllMenuItems() {
        List<MenuItem> menuItems = Arrays.asList(
                MenuItem.builder().id(1L).name("Pizza").price(10.99).build(),
                MenuItem.builder().id(2L).name("Burger").price(8.99).build()
        );
        when(menuItemRepository.findAll()).thenReturn(menuItems);

        List<MenuItem> result = menuService.getAllMenuItems();

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Burger", result.get(1).getName());
        verify(menuItemRepository).findAll();
    }
}

