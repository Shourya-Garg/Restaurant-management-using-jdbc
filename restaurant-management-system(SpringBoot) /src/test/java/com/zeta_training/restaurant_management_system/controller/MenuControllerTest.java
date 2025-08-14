package com.zeta_training.restaurant_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeta_training.restaurant_management_system.dto.MenuItemDTO;
import com.zeta_training.restaurant_management_system.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import java.util.Arrays;
import java.util.List;

import com.zeta_training.restaurant_management_system.entity.MenuItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class MenuControllerTest {
    @Mock
    private MenuService menuService;

    private MenuController menuController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        menuController = new MenuController(menuService);
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
    }

    @Test
    public void testAddMenuItem() throws Exception {
        MenuItemDTO menuItemDTO = MenuItemDTO.builder()
                .name("Test Item")
                .price(10.99)
                .build();
        MockHttpServletRequestBuilder mockRequest = post("/menuItem/addMenuItem")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(menuItemDTO));

        ResultActions resultActions = mockMvc.perform(mockRequest).andExpect(status().isOk());
        String response = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("Menu item added successfully", response);
    }
    @Test
    public void testGetAllMenuItems() throws Exception {
        MenuItem item1 = new MenuItem();
        item1.setId(1L);
        item1.setName("Test Item 1");
        item1.setPrice(10.99);

        MenuItem item2 = new MenuItem();
        item2.setId(2L);
        item2.setName("Test Item 2");
        item2.setPrice(15.99);

        List<MenuItem> menuItems = Arrays.asList(item1, item2);

        when(menuService.getAllMenuItems()).thenReturn(menuItems);

        MockHttpServletRequestBuilder mockRequest = get("/menuItem/getAllMenuItems");

        ResultActions resultActions = mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test Item 1")))
                .andExpect(jsonPath("$[0].price", is(10.99)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Test Item 2")))
                .andExpect(jsonPath("$[1].price", is(15.99)));
    }

    @Test
    public void testDeleteMenuItem() throws Exception {
        Long itemId = 1L;

        doNothing().when(menuService).deleteMenuItem(itemId);

        MockHttpServletRequestBuilder mockRequest = delete("/menuItem/deleteMenuItem/{id}", itemId);
        ResultActions resultActions = mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

        String response = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("Menu item deleted successfully", response);

        verify(menuService, times(1)).deleteMenuItem(itemId);
    }
}
