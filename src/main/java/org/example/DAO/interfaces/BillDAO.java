package org.example.DAO.interfaces;

import org.example.model.Bill;
import java.util.List;

public interface BillDAO {
    void generateBill(Bill bill);
    Bill getBillByOrderId(int orderId);
    Bill getBillById(int billId);
    List<Bill> getUnpaidBills();
    void updateBill(Bill bill);
    void deleteBill(int billId);
}
