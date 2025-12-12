package br.com.bookstore.shop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaleRequest(
    @NotNull Long bookId,
    @NotNull @Positive Integer quantity
) {}
