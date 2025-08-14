package com.zeta_training.restaurant_management_system.service;

import com.zeta_training.restaurant_management_system.dto.LoginDTO;
import com.zeta_training.restaurant_management_system.dto.RegisterDTO;
import com.zeta_training.restaurant_management_system.entity.Employee;
import com.zeta_training.restaurant_management_system.enumeration.Role;
import com.zeta_training.restaurant_management_system.repository.EmployeeRepository;
import com.zeta_training.restaurant_management_system.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class AuthorizationServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private JwtUtil jwtUtil;

    private AuthorizationService authorizationService;

    @BeforeEach
    public void setUp() {
        authorizationService = new AuthorizationService(employeeRepository, encoder, jwtUtil);
    }

    @Test
    public void testRegisterAdmin() {
        RegisterDTO request = RegisterDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .password("Password@123")
                .role(Role.ADMIN)
                .build();
        Mockito.when(employeeRepository.existsByRole(Role.ADMIN)).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> authorizationService.registerAdmin(request));
    }

    @Test
    public void testRegisterAdmin_WhenRoleIsNotAdmin() {
        RegisterDTO request = RegisterDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .password("Password@123")
                .role(Role.WAITER)
                .build();

        Mockito.when(employeeRepository.existsByRole(Role.ADMIN)).thenReturn(false);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> authorizationService.registerAdmin(request)
        );
        assertEquals("Only ADMIN role can be registered as an admin.", exception.getMessage());
    }

    @Test
    public void testRegisterAdmin_Successful() {
        RegisterDTO request = RegisterDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .password("Password@123")
                .role(Role.ADMIN)
                .build();

        Mockito.when(employeeRepository.existsByRole(Role.ADMIN)).thenReturn(false);

        authorizationService.registerAdmin(request);

        Mockito.verify(encoder).encode(request.getPassword());
        Mockito.verify(employeeRepository).save(Mockito.any(Employee.class));
    }

    @Test
    public void testRegisterRestaurantEmployee_WhenRoleIsAdmin() {
        RegisterDTO request = RegisterDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .password("Password@123")
                .role(Role.ADMIN)
                .build();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> authorizationService.registerRestaurantEmployee(request)
        );
        assertEquals("An admin user already exists.", exception.getMessage());
    }

    @Test
    public void testRegisterRestaurantEmployee_Successful() {
        RegisterDTO request = RegisterDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .password("Password@123")
                .role(Role.WAITER)
                .build();

        authorizationService.registerRestaurantEmployee(request);

        Mockito.verify(encoder).encode(request.getPassword());
        Mockito.verify(employeeRepository).save(Mockito.any(Employee.class));
    }

    @Test
    public void testLoginSuccessful() {
        LoginDTO request = new LoginDTO("john@gmail.com", "Password@123");
        Employee employee = Employee.builder()
                .email("john@gmail.com")
                .password("encodedPassword")
                .build();

        Mockito.when(employeeRepository.findByEmail("john@gmail.com")).thenReturn(java.util.Optional.of(employee));
        Mockito.when(encoder.matches("Password@123", "encodedPassword")).thenReturn(true);
        Mockito.when(jwtUtil.generateToken("john@gmail.com")).thenReturn("jwt-token");

        String token = authorizationService.login(request);

        assertEquals("jwt-token", token);
    }

    @Test
    public void testLoginUserNotFound() {
        LoginDTO request = new LoginDTO("nonexistent@gmail.com", "Password@123");

        Mockito.when(employeeRepository.findByEmail("nonexistent@gmail.com")).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authorizationService.login(request)
        );
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testLoginInvalidPassword() {
        LoginDTO request = new LoginDTO("john@gmail.com", "WrongPassword");
        Employee employee = Employee.builder()
                .email("john@gmail.com")
                .password("encodedPassword")
                .build();

        Mockito.when(employeeRepository.findByEmail("john@gmail.com")).thenReturn(java.util.Optional.of(employee));
        Mockito.when(encoder.matches("WrongPassword", "encodedPassword")).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authorizationService.login(request)
        );
        assertEquals("Invalid password", exception.getMessage());
    }
}
