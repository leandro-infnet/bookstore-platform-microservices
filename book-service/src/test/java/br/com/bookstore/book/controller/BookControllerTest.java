package br.com.bookstore.book.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bookstore.book.dto.BookRequest;
import br.com.bookstore.book.dto.BookResponse;
import br.com.bookstore.book.service.BookService;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService service;

    @Test
    @DisplayName("Should return 201 Created when creating a valid book")
    void shouldCreateBook() throws Exception {
        BookRequest request = new BookRequest("DevOps Pro", "Gene Kim", BigDecimal.valueOf(50.0), 10);
        BookResponse response = new BookResponse(1L, "DevOps Pro", "Gene Kim", BigDecimal.valueOf(50.0), 10);

        when(service.create(any(BookRequest.class))).thenReturn(response);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("DevOps Pro"));
    }

    @Test
    @DisplayName("Should return 200 OK and list of books")
    void shouldReturnAllBooks() throws Exception {
        when(service.findAll()).thenReturn(List.of(
            new BookResponse(1L, "A", "B", BigDecimal.TEN, 1)
        ));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @DisplayName("Should return 200 OK when finding book by ID")
    void shouldReturnBookById() throws Exception {
        BookResponse response = new BookResponse(1L, "A", "B", BigDecimal.TEN, 1);
        when(service.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
