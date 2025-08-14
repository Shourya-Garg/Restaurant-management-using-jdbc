package com.zeta_training.restaurant_management_system.controller;

import com.zeta_training.restaurant_management_system.dto.MenuItemDTO;
import com.zeta_training.restaurant_management_system.entity.MenuItem;
import com.zeta_training.restaurant_management_system.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menuItem")
@Slf4j
public class MenuController {
    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/addMenuItem")
    public ResponseEntity<String> addMenuItem(@RequestBody MenuItemDTO request) {
        log.info("Adding menu item: {}", request);
        menuService.addMenuItem(request);
        return ResponseEntity.ok("Menu item added successfully");
    }

    @GetMapping("/getAllMenuItems")
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuService.getAllMenuItems());
    }

    @DeleteMapping("/deleteMenuItem/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {
        log.info("Deleting menu item with ID: {}", id);
        menuService.deleteMenuItem(id);
        return ResponseEntity.ok("Menu item deleted successfully");
    }
}
