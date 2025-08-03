package org.example.DAO.interfaces;

import org.example.model.Customer;
import java.util.List;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    Customer getCustomerById(int customerId);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerId);
}
