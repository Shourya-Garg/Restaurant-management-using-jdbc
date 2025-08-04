package org.example;

import org.example.DAO.RestaurantDAOFactory;
import org.example.DAO.interfaces.*;
import org.example.model.*;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            RestaurantDAOFactory daoFactory = new RestaurantDAOFactory(conn);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== Restaurant Management System ===");
                System.out.println("1. User Management");
                System.out.println("2. Customer Management");
                System.out.println("3. Table Management");
                System.out.println("4. Order Management");
                System.out.println("5. Payment Management");
                System.out.println("6. Table Booking Management");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> handleUserManagement(daoFactory, scanner);
                    case 2 -> handleCustomerManagement(daoFactory, scanner);
                    case 3 -> handleTableManagement(daoFactory, scanner);
                    case 4 -> handleOrderManagement(daoFactory, scanner);
                    case 5 -> handlePaymentManagement(daoFactory, scanner);
                    case 6 -> handleTableBookingManagement(daoFactory, scanner);
                    case 7 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleUserManagement(RestaurantDAOFactory daoFactory, Scanner scanner) {
        UserDAO userDAO = daoFactory.getUserDAO();

        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Update User Email");
            System.out.println("4. Delete User");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    User user = new User();
                    System.out.print("Username: ");
                    user.setUsername(scanner.nextLine());
                    System.out.print("Password: ");
                    user.setPassword(scanner.nextLine());
                    System.out.print("Email: ");
                    user.setEmail(scanner.nextLine());
                    System.out.print("Phone: ");
                    user.setPhone(scanner.nextLine());
                    System.out.print("Role (Manager/Waiter/KitchenStaff): ");
                    user.setRole(User.Role.valueOf(scanner.nextLine()));
                    user.setActive(true);
                    user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    userDAO.addUser(user);
                    System.out.println("User added successfully.");
                    break;

                case 2:
                    List<User> users = userDAO.getAllUsers();
                    System.out.println("---- Users ----");
                    for (User u : users) {
                        System.out.println(u.getUserId() + ": " + u.getUsername() + " | " + u.getRole() + " | " + u.getEmail());
                    }
                    break;

                case 3:
                    System.out.print("Enter User ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("New Email: ");
                    String newEmail = scanner.nextLine();
                    User userToUpdate = userDAO.getUserById(updateId);
                    if (userToUpdate != null) {
                        userToUpdate.setEmail(newEmail);
                        userDAO.updateUser(userToUpdate);
                        System.out.println("Email updated.");
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter User ID to delete: ");
                    int deleteId = scanner.nextInt();
                    userDAO.deleteUser(deleteId);
                    System.out.println("User deleted.");
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void handleCustomerManagement(RestaurantDAOFactory daoFactory, Scanner scanner) {
        CustomerDAO customerDAO = daoFactory.getCustomerDAO();

        while (true) {
            System.out.println("\n=== Customer Management ===");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Customer customer = new Customer();
                    System.out.print("Name: ");
                    customer.setName(scanner.nextLine());
                    System.out.print("Phone: ");
                    customer.setPhone(scanner.nextLine());
                    System.out.print("Email: ");
                    customer.setEmail(scanner.nextLine());
                    customer.setActive(true);
                    customer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    customerDAO.addCustomer(customer);
                    System.out.println("Customer added successfully.");
                    break;

                case 2:
                    List<Customer> customers = customerDAO.getAllCustomers();
                    System.out.println("---- Customers ----");
                    for (Customer c : customers) {
                        System.out.println(c.getCustomerId() + ": " + c.getName() + " | " + c.getPhone() + " | " + c.getEmail());
                    }
                    break;

                case 3:
                    System.out.print("Enter Customer ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    Customer customerToUpdate = customerDAO.getCustomerById(updateId);
                    if (customerToUpdate != null) {
                        System.out.print("New Name: ");
                        customerToUpdate.setName(scanner.nextLine());
                        System.out.print("New Phone: ");
                        customerToUpdate.setPhone(scanner.nextLine());
                        System.out.print("New Email: ");
                        customerToUpdate.setEmail(scanner.nextLine());
                        customerDAO.updateCustomer(customerToUpdate);
                        System.out.println("Customer updated.");
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Customer ID to delete: ");
                    int deleteId = scanner.nextInt();
                    customerDAO.deleteCustomer(deleteId);
                    System.out.println("Customer deleted.");
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    private static void handleTableManagement(RestaurantDAOFactory daoFactory, Scanner scanner) {
        TableDAO tableDAO = daoFactory.getTableDAO();

        while (true) {
            System.out.println("\n=== Table Management ===");
            System.out.println("1. Add Table");
            System.out.println("2. View All Tables");
            System.out.println("3. Update Table");
            System.out.println("4. Delete Table");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    Table table = new Table();
                    System.out.print("Table Number: ");
                    table.setTableNumber(scanner.nextInt());
                    System.out.print("Capacity: ");
                    table.setCapacity(scanner.nextInt());
                    table.setStatus(Table.Status.Available);
                    tableDAO.addTable(table);
                    System.out.println("Table added successfully.");
                }
                case 2 -> {
                    List<Table> tables = tableDAO.getAllTables();
                    System.out.println("---- Tables ----");
                    for (Table t : tables) {
                        System.out.println(t.getTableId() + ": Table " + t.getTableNumber() + " | Capacity: " + t.getCapacity() + " | Status: " + t.getStatus());
                    }
                }
                case 3 -> {
                    List<Table> allTables = tableDAO.getAllTables();
                    System.out.println("---- Tables ----");
                    for (Table t : allTables) {
                        System.out.println(t.getTableId() + ": Table " + t.getTableNumber() + " | Capacity: " + t.getCapacity() + " | Status: " + t.getStatus());
                    }
                    System.out.print("Enter Table ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    Table tableToUpdate = tableDAO.getTableById(updateId);
                    if (tableToUpdate != null) {
                        System.out.print("New Table Number: ");
                        tableToUpdate.setTableNumber(scanner.nextInt());
                        System.out.print("New Capacity: ");
                        tableToUpdate.setCapacity(scanner.nextInt());
                        scanner.nextLine();
                        System.out.print("Status (Available/Occupied/Booked/Reserved): ");
                        tableToUpdate.setStatus(Table.Status.valueOf(scanner.nextLine()));
                        tableDAO.updateTable(tableToUpdate);
                        System.out.println("Table updated.");
                    } else {
                        System.out.println("Table not found.");
                    }
                }
                case 4 -> {
                    List<Table> allTables = tableDAO.getAllTables();
                    System.out.println("---- Tables ----");
                    for (Table t : allTables) {
                        System.out.println(t.getTableId() + ": Table " + t.getTableNumber() + " | Capacity: " + t.getCapacity() + " | Status: " + t.getStatus());
                    }
                    System.out.print("Enter Table ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    tableDAO.deleteTable(deleteId);
                    System.out.println("Table deleted.");
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
    private static void handleOrderManagement(RestaurantDAOFactory daoFactory, Scanner scanner) {
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        TableDAO tableDAO = daoFactory.getTableDAO();

        while (true) {
            System.out.println("\n=== Order Management ===");
            System.out.println("1. Add Order");
            System.out.println("2. View All Orders");
            System.out.println("3. Update Order Status");
            System.out.println("4. Delete Order");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Show available tables before taking input
                    List<Table> availableTables = tableDAO.getAllTables();
                    System.out.println("---- Available Tables ----");
                    for (Table t : availableTables) {
                        System.out.println("ID: " + t.getTableId() + " | Table #: " + t.getTableNumber() + " | Status: " + t.getStatus());
                    }

                    Order order = new Order();

                    System.out.print("Table ID: ");
                    int tableId = scanner.nextInt();
                    scanner.nextLine();

                    // Check if table ID is valid
                    Table selectedTable = tableDAO.getTableById(tableId);
                    if (selectedTable == null) {
                        System.out.println("Invalid Table ID. Please add the table first.");
                        break;
                    }

                    System.out.print("Waiter ID: ");
                    order.setWaiterId(scanner.nextInt());
                    scanner.nextLine();

                    order.setTableId(tableId);
                    order.setOrderTime(new Timestamp(System.currentTimeMillis()));
                    order.setStatus(Order.Status.Placed);
                    orderDAO.addOrder(order);
                    System.out.println("Order added successfully.");
                    break;

                case 2:
                    List<Order> orders = orderDAO.getAllOrders();
                    System.out.println("---- Orders ----");
                    for (Order o : orders) {
                        System.out.println(o.getOrderId() + ": Table " + o.getTableId() + " | Waiter: " + o.getWaiterId() + " | Status: " + o.getStatus());
                    }
                    break;

                case 3:
                    System.out.print("Enter Order ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    Order orderToUpdate = orderDAO.getOrderById(updateId);
                    if (orderToUpdate != null) {
                        System.out.print("New Status (Placed/Preparing/Served/Completed): ");
                        String statusInput = scanner.nextLine().trim();

                        try {
                            Order.Status newStatus = Order.Status.valueOf(statusInput);
                            orderToUpdate.setStatus(newStatus);
                            orderDAO.updateOrder(orderToUpdate);
                            System.out.println("Order status updated.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid status. Please use one of: Placed, Preparing, Served, Completed.");
                        }
                    } else {
                        System.out.println("Order not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Order ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    orderDAO.deleteOrder(deleteId);
                    System.out.println("Order deleted.");
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }


        }
    }

    private static void handlePaymentManagement(RestaurantDAOFactory daoFactory, Scanner scanner) {
        PaymentDAO paymentDAO = daoFactory.getPaymentDAO();
        BillDAO billDAO = daoFactory.getBillDAO();

        while (true) {
            System.out.println("\n=== Payment Management ===");
            System.out.println("1. Record Payment");
            System.out.println("2. View All Payments");
            System.out.println("3. View Payment by Bill ID");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Show unpaid bills first
                    List<Bill> unpaidBills = billDAO.getUnpaidBills();
                    if (unpaidBills.isEmpty()) {
                        System.out.println("No unpaid bills found.");
                        break;
                    }
                    
                    System.out.println("---- Unpaid Bills ----");
                    for (Bill b : unpaidBills) {
                        System.out.println("Bill ID: " + b.getBillId() + " | Order: " + b.getOrderId() + 
                                         " | Amount: $" + b.getFinalAmount() + " | Status: " + b.getPaymentStatus());
                    }
                    
                    Payment payment = new Payment();
                    System.out.print("Bill ID: ");
                    int billId = scanner.nextInt();
                    
                    // Validate bill exists and is unpaid
                    Bill selectedBill = billDAO.getBillById(billId);
                    if (selectedBill == null) {
                        System.out.println("Invalid Bill ID.");
                        break;
                    }
                    if (selectedBill.getPaymentStatus() == Bill.PaymentStatus.Paid) {
                        System.out.println("Bill already paid.");
                        break;
                    }
                    
                    payment.setBillId(billId);
                    scanner.nextLine();
                    System.out.print("Payment Method (Cash/Card/UPI/Wallet): ");
                    payment.setPaymentMethod(Payment.PaymentMethod.valueOf(scanner.nextLine()));
                    System.out.print("Amount Paid: ");
                    payment.setAmountPaid(scanner.nextDouble());
                    payment.setPaymentTime(new Timestamp(System.currentTimeMillis()));
                    payment.setStatus(Payment.Status.Successful);
                    paymentDAO.recordPayment(payment);
                    
                    // Update bill status
                    selectedBill.setPaymentStatus(Bill.PaymentStatus.Paid);
                    billDAO.updateBill(selectedBill);
                    
                    System.out.println("Payment recorded successfully.");
                    break;

                case 2:
                    List<Payment> payments = paymentDAO.getAllPayments();
                    System.out.println("---- Payments ----");
                    for (Payment p : payments) {
                        System.out.println(p.getPaymentId() + ": Bill " + p.getBillId() + 
                                         " | Method: " + p.getPaymentMethod() + " | Amount: $" + p.getAmountPaid() +
                                         " | Status: " + p.getStatus());
                    }
                    break;

                case 3:
                    System.out.print("Enter Bill ID: ");
                    int searchBillId = scanner.nextInt();
                    Payment paymentByBill = paymentDAO.getPaymentByBillId(searchBillId);
                    if (paymentByBill != null) {
                        System.out.println("Payment ID: " + paymentByBill.getPaymentId() + 
                                         " | Amount: $" + paymentByBill.getAmountPaid() + 
                                         " | Method: " + paymentByBill.getPaymentMethod() +
                                         " | Status: " + paymentByBill.getStatus());
                    } else {
                        System.out.println("Payment not found for this bill.");
                    }
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void handleTableBookingManagement(RestaurantDAOFactory daoFactory, Scanner scanner) {
        TableBookingDAO bookingDAO = daoFactory.getTableBookingDAO();
        TableDAO tableDAO = daoFactory.getTableDAO();
        CustomerDAO customerDAO = daoFactory.getCustomerDAO();

        while (true) {
            System.out.println("\n=== Table Booking Management ===");
            System.out.println("1. Add Booking");
            System.out.println("2. View All Bookings");
            System.out.println("3. Update Booking Status");
            System.out.println("4. Delete Booking");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Show available customers
                    List<Customer> customers = customerDAO.getAllCustomers();
                    System.out.println("---- Available Customers ----");
                    for (Customer c : customers) {
                        System.out.println("ID: " + c.getCustomerId() + " | Name: " + c.getName() + 
                                         " | Phone: " + c.getPhone());
                    }
                    
                    // Show available tables
                    List<Table> tables = tableDAO.getAllTables();
                    System.out.println("---- Available Tables ----");
                    for (Table t : tables) {
                        System.out.println("ID: " + t.getTableId() + " | Table #: " + t.getTableNumber() + 
                                         " | Capacity: " + t.getCapacity() + " | Status: " + t.getStatus());
                    }

                    TableBooking booking = new TableBooking();
                    System.out.print("Customer ID: ");
                    int customerId = scanner.nextInt();
                    
                    // Validate customer exists
                    Customer selectedCustomer = customerDAO.getCustomerById(customerId);
                    if (selectedCustomer == null) {
                        System.out.println("Invalid Customer ID.");
                        break;
                    }
                    
                    System.out.print("Table ID: ");
                    int tableId = scanner.nextInt();
                    
                    // Validate table exists
                    Table selectedTable = tableDAO.getTableById(tableId);
                    if (selectedTable == null) {
                        System.out.println("Invalid Table ID.");
                        break;
                    }
                    
                    booking.setCustomerId(customerId);
                    booking.setTableId(tableId);
                    scanner.nextLine();
                    System.out.print("Booking Date (YYYY-MM-DD): ");
                    booking.setBookingDate(Date.valueOf(scanner.nextLine()));
                    System.out.print("Booking Time (HH:MM:SS): ");
                    booking.setBookingTime(Time.valueOf(scanner.nextLine()));
                    booking.setStatus(TableBooking.Status.Confirmed);
                    booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    bookingDAO.addBooking(booking);
                    
                    // Update table status to Booked
                    selectedTable.setStatus(Table.Status.Booked);
                    tableDAO.updateTable(selectedTable);
                    
                    System.out.println("Booking added successfully.");
                    break;

                case 2:
                    List<TableBooking> bookings = bookingDAO.getAllBookings();
                    System.out.println("---- Bookings ----");
                    for (TableBooking b : bookings) {
                        System.out.println(b.getBookingId() + ": Customer " + b.getCustomerId() + 
                                         " | Table " + b.getTableId() + " | Date: " + b.getBookingDate() + 
                                         " | Time: " + b.getBookingTime() + " | Status: " + b.getStatus());
                    }
                    break;

                case 3:
                    // Show all bookings first
                    List<TableBooking> allBookings = bookingDAO.getAllBookings();
                    System.out.println("---- All Bookings ----");
                    for (TableBooking b : allBookings) {
                        System.out.println(b.getBookingId() + ": Customer " + b.getCustomerId() + 
                                         " | Table " + b.getTableId() + " | Date: " + b.getBookingDate() + 
                                         " | Status: " + b.getStatus());
                    }
                    
                    System.out.print("Enter Booking ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    TableBooking bookingToUpdate = bookingDAO.getBookingById(updateId);
                    if (bookingToUpdate != null) {
                        System.out.print("New Status (Confirmed/Cancelled/Completed): ");
                        String statusInput = scanner.nextLine().trim();
                        
                        try {
                            TableBooking.Status newStatus = TableBooking.Status.valueOf(statusInput);
                            bookingToUpdate.setStatus(newStatus);
                            bookingDAO.updateBooking(bookingToUpdate);
                            
                            // Update table status based on booking status
                            Table bookedTable = tableDAO.getTableById(bookingToUpdate.getTableId());
                            if (newStatus == TableBooking.Status.Cancelled || newStatus == TableBooking.Status.Completed) {
                                bookedTable.setStatus(Table.Status.Available);
                                tableDAO.updateTable(bookedTable);
                            }
                            
                            System.out.println("Booking status updated.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid status. Please use: Confirmed, Cancelled, or Completed.");
                        }
                    } else {
                        System.out.println("Booking not found.");
                    }
                    break;

                case 4:
                    // Show all bookings first
                    List<TableBooking> bookingsToDelete = bookingDAO.getAllBookings();
                    System.out.println("---- All Bookings ----");
                    for (TableBooking b : bookingsToDelete) {
                        System.out.println(b.getBookingId() + ": Customer " + b.getCustomerId() + 
                                         " | Table " + b.getTableId() + " | Date: " + b.getBookingDate() + 
                                         " | Status: " + b.getStatus());
                    }
                    
                    System.out.print("Enter Booking ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    
                    // Get booking details before deletion to update table status
                    TableBooking bookingToDelete = bookingDAO.getBookingById(deleteId);
                    if (bookingToDelete != null) {
                        Table tableToFree = tableDAO.getTableById(bookingToDelete.getTableId());
                        tableToFree.setStatus(Table.Status.Available);
                        tableDAO.updateTable(tableToFree);
                    }
                    
                    bookingDAO.deleteBooking(deleteId);
                    System.out.println("Booking deleted.");
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
