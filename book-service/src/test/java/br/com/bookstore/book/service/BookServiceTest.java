package br.com.bookstore.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.bookstore.book.domain.Book;
import br.com.bookstore.book.dto.BookRequest;
import br.com.bookstore.book.dto.BookResponse;
import br.com.bookstore.book.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository;

    @Test
    @DisplayName("Should create a book successfully and return response DTO")
    void shouldCreateBookSuccessfully() {
        BookRequest request = new BookRequest("Clean Code", "Uncle Bob", BigDecimal.TEN, 10);

        Book bookEntity = new Book(1L, "Clean Code", "Uncle Bob", BigDecimal.TEN, 10);

        when(repository.save(any(Book.class))).thenReturn(bookEntity);

        BookResponse response = service.create(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Clean Code", response.title());
        assertEquals("Uncle Bob", response.author());

        verify(repository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should return a list of BookResponse")
    void shouldReturnAllBooks() {
        List<Book> books = List.of(
            new Book(1L, "Book A", "Auth A", BigDecimal.ONE, 5),
            new Book(2L, "Book B", "Auth B", BigDecimal.TEN, 2)
        );
        when(repository.findAll()).thenReturn(books);

        List<BookResponse> result = service.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book A", result.get(0).title());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return BookResponse when ID exists")
    void shouldFindBookById() {
        Long id = 1L;
        Book book = new Book(id, "Java", "Author", BigDecimal.TEN, 5);
        when(repository.findById(id)).thenReturn(Optional.of(book));

        BookResponse result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("Java", result.title());
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should throw RuntimeException when ID does not exist")
    void shouldThrowExceptionWhenBookNotFound() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.findById(id));
        assertEquals("Book not found ID: 99", exception.getMessage());

        verify(repository, times(1)).findById(id);
    }
}
