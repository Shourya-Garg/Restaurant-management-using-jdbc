package org.example.model;

import java.sql.Timestamp;

public class Customer {
    private int customerId;
    private String name;
    private String phone;
    private String email;
    private boolean isActive;
    private Timestamp createdAt;

    public Customer() {}

    public Customer(int customerId, String name, String phone, String email, boolean isActive, Timestamp createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}
