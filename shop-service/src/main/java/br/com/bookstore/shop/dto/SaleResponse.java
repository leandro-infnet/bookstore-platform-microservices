package br.com.bookstore.shop.dto;

import java.math.BigDecimal;

import br.com.bookstore.shop.domain.Sale;

public record SaleResponse(
    Long id,
    Long bookId,
    Integer quantity,
    BigDecimal totalPrice
) {
    public SaleResponse(Sale sale) {
        this(sale.getId(), sale.getBookId(), sale.getQuantity(), sale.getTotalPrice());
    }
}
