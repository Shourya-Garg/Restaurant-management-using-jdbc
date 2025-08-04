package org.example.DAO.interfaces;

import org.example.model.Payment;
import java.util.List;

public interface PaymentDAO {
    void recordPayment(Payment payment);
    Payment getPaymentByBillId(int billId);
    List<Payment> getAllPayments();
}
