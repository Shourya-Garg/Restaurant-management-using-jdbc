package org.example.DAO.interfaces;

import org.example.model.Bill;

public interface BillDAO {
    void generateBill(Bill bill);
    Bill getBillByOrderId(int orderId);
    void updateBill(Bill bill);
    void deleteBill(int billId);
}
