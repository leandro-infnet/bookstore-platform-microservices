package br.com.bookstore.book.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookRequest(
    @NotBlank(message = "Title is required") String title,
    @NotBlank(message = "Author is required") String author,
    @NotNull @Positive(message = "Price must be positive") BigDecimal price,
    @NotNull @Positive(message = "Stock must be positive") Integer availableStock
) {}
