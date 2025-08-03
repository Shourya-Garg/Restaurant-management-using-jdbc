package org.example.DAO.interfaces;

import org.example.model.Employee;
import java.util.List;

public interface EmployeeDAO {
    void addEmployee(Employee employee);
    Employee getEmployeeById(int employeeId);
    List<Employee> getAllEmployees();
    void updateEmployee(Employee employee);
    void deleteEmployee(int employeeId);
}
