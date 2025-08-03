package org.example;

import org.example.DAO.RestaurantDAOFactory;
import org.example.DAO.interfaces.UserDAO;
import org.example.model.User;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            RestaurantDAOFactory daoFactory = new RestaurantDAOFactory(conn);
            UserDAO userDAO = daoFactory.getUserDAO();

            // Test: Add User
            User user = new User();
            user.setUsername("test_user");
            user.setPassword("securepassword");
            user.setEmail("test@example.com");
            user.setPhone("1234567890");
            user.setRole(User.Role.Manager);
            user.setActive(true);
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            userDAO.addUser(user);

            // Test: Get all users
            List<User> users = userDAO.getAllUsers();
            users.forEach(u -> System.out.println(u.getUsername() + " - " + u.getRole()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
