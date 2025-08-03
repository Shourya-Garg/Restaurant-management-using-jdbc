package org.example.DAO.impl;

import org.example.DAO.interfaces.OrderDAO;
import org.example.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private final Connection connection;

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (table_id, customer_id, status, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getTableId());
            stmt.setInt(2, order.getOrderId());
            stmt.setString(3, order.getStatus().toString());
            stmt.setTimestamp(4, order.getOrderTime());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapRowToOrder(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) orders.add(mapRowToOrder(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE orders SET table_id=?, customer_id=?, status=? WHERE order_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getTableId());
            stmt.setInt(2, order.getOrderId());
            stmt.setString(3, order.getStatus().toString());
            stmt.setInt(4, order.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Order mapRowToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setTableId(rs.getInt("table_id"));
        order.setWaiterId(rs.getInt("customer_id"));
        order.setStatus(Order.Status.valueOf(rs.getString("status")));
        order.setOrderTime(rs.getTimestamp("created_at"));
        return order;
    }
}
