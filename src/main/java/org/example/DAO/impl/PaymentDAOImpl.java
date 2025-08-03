package org.example.DAO.impl;

import org.example.DAO.interfaces.PaymentDAO;
import org.example.model.Payment;

import java.sql.*;

public class PaymentDAOImpl implements PaymentDAO {
    private final Connection connection;

    public PaymentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void recordPayment(Payment payment) {
        String sql = "INSERT INTO payments (bill_id, payment_method, amount_paid, paid_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, payment.getBillId());
            stmt.setString(2, payment.getPaymentMethod().toString());
            stmt.setDouble(3, payment.getAmountPaid());
            stmt.setTimestamp(4, payment.getPaymentTime());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Payment getPaymentByBillId(int billId) {
        String sql = "SELECT * FROM payments WHERE bill_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setBillId(rs.getInt("bill_id"));
                payment.setPaymentMethod(Payment.PaymentMethod.valueOf(rs.getString("payment_method")));
                payment.setAmountPaid(rs.getDouble("amount_paid"));
                payment.setPaymentTime(rs.getTimestamp("paid_at"));
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
