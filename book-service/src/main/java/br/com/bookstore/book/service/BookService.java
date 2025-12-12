package br.com.bookstore.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.bookstore.book.domain.Book;
import br.com.bookstore.book.dto.BookRequest;
import br.com.bookstore.book.dto.BookResponse;
import br.com.bookstore.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public BookResponse create(BookRequest request) {
        Book entity = new Book();
        entity.setTitle(request.title());
        entity.setAuthor(request.author());
        entity.setPrice(request.price());
        entity.setAvailableStock(request.availableStock());

        Book saved = repository.save(entity);
        return new BookResponse(saved);
    }

    public List<BookResponse> findAll() {
        return repository.findAll().stream()
                .map(BookResponse::new)
                .toList();
    }

    public BookResponse findById(Long id) {
        return repository.findById(id)
                .map(BookResponse::new)
                .orElseThrow(() -> new RuntimeException("Book not found ID: " + id));
    }
}
