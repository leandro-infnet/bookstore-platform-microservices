package br.com.bookstore.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bookstore.shop.dto.SaleRequest;
import br.com.bookstore.shop.dto.SaleResponse;
import br.com.bookstore.shop.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService service;

    @PostMapping
    public ResponseEntity<SaleResponse> create(@RequestBody @Valid SaleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.makeSale(request));
    }
}
