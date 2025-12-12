package br.com.bookstore.shop.client;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", url = "${BOOK_SERVICE_URL:http://localhost:8081/books}")
public interface BookClient {

    @GetMapping("/{id}")
    BookDTO findBookById(@PathVariable("id") Long id);

    record BookDTO(Long id, String title, BigDecimal price, Integer availableStock) {}
}
