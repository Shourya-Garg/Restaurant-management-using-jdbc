package com.zeta_training.restaurant_management_system.service;

import com.zeta_training.restaurant_management_system.dto.MenuItemDTO;
import com.zeta_training.restaurant_management_system.entity.MenuItem;
import com.zeta_training.restaurant_management_system.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public void addMenuItem(MenuItemDTO request) {
        MenuItem menuItem = MenuItem.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        menuItemRepository.delete(menuItem);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }
}
