package br.com.bookstore.shop.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.bookstore.shop.client.BookClient;
import br.com.bookstore.shop.domain.Sale;
import br.com.bookstore.shop.dto.SaleRequest;
import br.com.bookstore.shop.dto.SaleResponse;
import br.com.bookstore.shop.repository.SaleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final SaleRepository repository;
    private final BookClient bookClient;

    public SaleResponse makeSale(SaleRequest request) {
        BookClient.BookDTO book = bookClient.findBookById(request.bookId());

        BigDecimal total = book.price().multiply(BigDecimal.valueOf(request.quantity()));

        Sale sale = new Sale(null, book.id(), request.quantity(), total, LocalDateTime.now());
        Sale savedSale = repository.save(sale);

        return new SaleResponse(savedSale);
    }
}
