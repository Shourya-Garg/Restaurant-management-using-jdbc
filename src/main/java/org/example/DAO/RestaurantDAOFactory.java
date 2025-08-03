package org.example.DAO;

import org.example.DAO.impl.*;
import org.example.DAO.interfaces.*;

import java.sql.Connection;

public class RestaurantDAOFactory {

    private final Connection connection;

    public RestaurantDAOFactory(Connection connection) {
        this.connection = connection;
    }

    public UserDAO getUserDAO() {
        return new UserDAOImpl(connection);
    }

    public CustomerDAO getCustomerDAO() {
        return new CustomerDAOImpl(connection);
    }

    public TableDAO getTableDAO() {
        return new TableDAOImpl(connection);
    }

    public TableBookingDAO getTableBookingDAO() {
        return new TableBookingDAOImpl(connection);
    }

    public MenuItemDAO getMenuItemDAO() {
        return new MenuItemDAOImpl(connection);
    }

    public OrderDAO getOrderDAO() {
        return new OrderDAOImpl(connection);
    }

    public OrderItemDAO getOrderItemDAO() {
        return new OrderItemDAOImpl(connection);
    }

    public BillDAO getBillDAO() {
        return new BillDAOImpl(connection);
    }

    public PaymentDAO getPaymentDAO() {
        return new PaymentDAOImpl(connection);
    }

    public EmployeeDAO getEmployeeDAO() {
        return new EmployeeDAOImpl(connection);
    }

    public SalesReportDAO getSalesReportDAO() {
        return new SalesReportDAOImpl(connection);
    }
}
