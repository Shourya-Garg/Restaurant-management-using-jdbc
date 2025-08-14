package com.zeta_training.restaurant_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeta_training.restaurant_management_system.dto.LoginDTO;
import com.zeta_training.restaurant_management_system.dto.RegisterDTO;
import com.zeta_training.restaurant_management_system.enumeration.Role;
import com.zeta_training.restaurant_management_system.service.AuthorizationService;
import com.zeta_training.restaurant_management_system.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class AuthorizationControllerTest {
    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private JwtUtil jwtUtil;

    private MockMvc mockMvc;
    private AuthorizationController authorizationController;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        authorizationController = new AuthorizationController(authorizationService, jwtUtil);
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(authorizationController).build();
    }

    @Test
    public void testRegisterUser_success() throws Exception {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .role(Role.ADMIN)
                .password("password123")
                .email("john@gmail.com")
                .build();
        Mockito.doNothing().when(authorizationService).registerAdmin(registerDTO);
        MockHttpServletRequestBuilder mockRequest = post("/auth/registerAdmin").
                contentType("application/json").content(objectMapper.writeValueAsString(registerDTO));

        ResultActions resultActions = mockMvc.perform(mockRequest).andExpect(status().isOk());
        String response = resultActions.andReturn().getResponse().getContentAsString();
        Mockito.verify(authorizationService, Mockito.times(1)).registerAdmin(registerDTO);
        assertEquals("Admin registered successfully", response);
    }

    @Test
    public void testLogin_success() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
                .email("john@gmail.com")
                .password("password123")
                .build();
        String mockToken = "mock-jwt-token";
        Mockito.when(authorizationService.login(loginDTO)).thenReturn(mockToken);

        MockHttpServletRequestBuilder mockRequest = get("/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginDTO));

        ResultActions resultActions = mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
        String response = resultActions.andReturn().getResponse().getContentAsString();
        Mockito.verify(authorizationService, Mockito.times(1)).login(loginDTO);
        assertEquals(mockToken, response);
    }
}
