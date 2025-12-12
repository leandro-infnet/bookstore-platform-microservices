package br.com.bookstore.shop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bookstore.shop.dto.SaleRequest;
import br.com.bookstore.shop.dto.SaleResponse;
import br.com.bookstore.shop.service.ShopService;

@WebMvcTest(ShopController.class)
class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ShopService service;

    @Test
    @DisplayName("Should return 201 Created when making a valid sale")
    void shouldCreateSale() throws Exception {
        SaleRequest request = new SaleRequest(1L, 2);

        SaleResponse response = new SaleResponse(100L, 1L, 2, BigDecimal.valueOf(50.00));

        when(service.makeSale(any(SaleRequest.class))).thenReturn(response);

        mockMvc.perform(post("/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.totalPrice").value(50.00));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when request is invalid")
    void shouldReturnBadRequest() throws Exception {
        String invalidJson = """
            {
                "bookId": null,
                "quantity": -5
            }
        """;

        mockMvc.perform(post("/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
