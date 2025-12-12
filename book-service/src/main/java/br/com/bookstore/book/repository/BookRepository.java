package br.com.bookstore.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bookstore.book.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
