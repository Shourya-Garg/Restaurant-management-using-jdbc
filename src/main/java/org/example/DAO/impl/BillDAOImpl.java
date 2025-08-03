package org.example.DAO.impl;

import org.example.DAO.interfaces.BillDAO;
import org.example.model.Bill;

import java.sql.*;

public class BillDAOImpl implements BillDAO {
    private final Connection connection;

    public BillDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void generateBill(Bill bill) {
        String sql = "INSERT INTO bills (order_id, total_amount, generated_at) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bill.getOrderId());
            stmt.setDouble(2, bill.getTotalAmount());
            stmt.setTimestamp(3, bill.getGeneratedAt());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bill getBillByOrderId(int orderId) {
        String sql = "SELECT * FROM bills WHERE order_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setOrderId(rs.getInt("order_id"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setGeneratedAt(rs.getTimestamp("generated_at"));
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateBill(Bill bill) {
        String sql = "UPDATE bills SET total_amount=?, generated_at=? WHERE bill_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, bill.getTotalAmount());
            stmt.setTimestamp(2, bill.getGeneratedAt());
            stmt.setInt(3, bill.getBillId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBill(int billId) {
        String sql = "DELETE FROM bills WHERE bill_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
