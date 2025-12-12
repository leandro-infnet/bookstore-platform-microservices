package br.com.bookstore.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.bookstore.shop.client.BookClient;
import br.com.bookstore.shop.domain.Sale;
import br.com.bookstore.shop.dto.SaleRequest;
import br.com.bookstore.shop.dto.SaleResponse;
import br.com.bookstore.shop.repository.SaleRepository;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @InjectMocks
    private ShopService service;

    @Mock
    private SaleRepository repository;

    @Mock
    private BookClient bookClient;

    @Test
    @DisplayName("Should make a sale successfully")
    void shouldMakeSale() {
        Long bookId = 1L;
        SaleRequest request = new SaleRequest(bookId, 2);

        BookClient.BookDTO mockBook = new BookClient.BookDTO(bookId, "TDD Book", BigDecimal.TEN, 5);
        when(bookClient.findBookById(bookId)).thenReturn(mockBook);

        Sale savedSale = new Sale(1L, bookId, 2, BigDecimal.valueOf(20.0), null);
        when(repository.save(any(Sale.class))).thenReturn(savedSale);

        SaleResponse response = service.makeSale(request);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(20.0), response.totalPrice());

        verify(bookClient).findBookById(bookId);
        verify(repository).save(any(Sale.class));
    }
}
