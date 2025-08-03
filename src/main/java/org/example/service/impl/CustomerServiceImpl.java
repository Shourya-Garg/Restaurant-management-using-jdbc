package org.example.service.impl;

import org.example.DAO.RestaurantDAOFactory;
import org.example.DAO.interfaces.CustomerDAO;
import org.example.model.Customer;
import org.example.service.interfaces.CustomerService;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    
    @Override
    public void addCustomer(Customer customer) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            RestaurantDAOFactory factory = new RestaurantDAOFactory(conn);
            CustomerDAO customerDAO = factory.getCustomerDAO();
            customerDAO.addCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer getCustomerById(int customerId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            RestaurantDAOFactory factory = new RestaurantDAOFactory(conn);
            CustomerDAO customerDAO = factory.getCustomerDAO();
            return customerDAO.getCustomerById(customerId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            RestaurantDAOFactory factory = new RestaurantDAOFactory(conn);
            CustomerDAO customerDAO = factory.getCustomerDAO();
            return customerDAO.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            RestaurantDAOFactory factory = new RestaurantDAOFactory(conn);
            CustomerDAO customerDAO = factory.getCustomerDAO();
            customerDAO.updateCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            RestaurantDAOFactory factory = new RestaurantDAOFactory(conn);
            CustomerDAO customerDAO = factory.getCustomerDAO();
            customerDAO.deleteCustomer(customerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
