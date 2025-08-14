package com.zeta_training.restaurant_management_system.service;

import com.zeta_training.restaurant_management_system.dto.LoginDTO;
import com.zeta_training.restaurant_management_system.dto.RegisterDTO;
import com.zeta_training.restaurant_management_system.entity.Employee;
import com.zeta_training.restaurant_management_system.enumeration.Role;
import com.zeta_training.restaurant_management_system.repository.EmployeeRepository;
import com.zeta_training.restaurant_management_system.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorizationService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder encoder;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthorizationService(EmployeeRepository employeeRepository, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }


    public void registerAdmin(RegisterDTO request) {
        if (employeeRepository.existsByRole(Role.ADMIN)) {
            log.error("An admin user already exists.");
            throw new IllegalStateException("An admin user already exists.");
        }
        if (request.getRole() != Role.ADMIN) {
            log.error("Only ADMIN role can be registered as an admin.");
            throw new IllegalStateException("Only ADMIN role can be registered as an admin.");
        }
        saveData(request);
    }

    public void registerRestaurantEmployee(RegisterDTO request) {
        if (request.getRole()==Role.ADMIN) {
            log.error("Cannot register an admin as a restaurant employee.");
            throw new IllegalStateException("An admin user already exists.");
        }
        saveData(request);
    }

    public String login(LoginDTO request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), employee.getPassword())) {
            log.error("Invalid password for user: {}", request.getEmail());
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(employee.getEmail());
    }

    private void validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!password.matches(regex)) {
            log.error("Password does not meet the required criteria");
            throw new InvalidPasswordException(
                    "Password does not meet the required criteria"
            );
        }
    }

    private void saveData(RegisterDTO request) {
        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        validatePassword(request.getPassword());
        employeeRepository.save(employee);
    }
}
