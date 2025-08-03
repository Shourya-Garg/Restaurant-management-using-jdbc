package org.example.DAO.interfaces;

import org.example.model.MenuItem;
import java.util.List;

public interface MenuItemDAO {
    void addMenuItem(MenuItem menuItem);
    MenuItem getMenuItemById(int itemId);
    List<MenuItem> getAllMenuItems();
    void updateMenuItem(MenuItem menuItem);
    void deleteMenuItem(int itemId);
}
