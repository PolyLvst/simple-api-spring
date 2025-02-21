package com.polylvst.simplepos.controllers;

import com.polylvst.simplepos.domain.dtos.ProductDto;
import com.polylvst.simplepos.mappers.ProductMapper;
import com.polylvst.simplepos.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> listProducts() {
        List<ProductDto> products = productService.listProducts()
                .stream()
                .map(product -> productMapper.toDto(product))
                .toList();
        return ResponseEntity.ok(products);
    }
}
