package org.example.DAO.interfaces;

import org.example.model.Payment;

public interface PaymentDAO {
    void recordPayment(Payment payment);
    Payment getPaymentByBillId(int billId);
}
