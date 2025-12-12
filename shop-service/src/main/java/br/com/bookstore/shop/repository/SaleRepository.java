package br.com.bookstore.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bookstore.shop.domain.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {}
