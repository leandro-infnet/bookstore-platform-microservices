package br.com.bookstore.book.dto;

import java.math.BigDecimal;

import br.com.bookstore.book.domain.Book;

public record BookResponse(
    Long id,
    String title,
    String author,
    BigDecimal price,
    Integer availableStock
) {
    public BookResponse(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getAvailableStock());
    }
}
