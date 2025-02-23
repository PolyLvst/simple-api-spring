package com.polylvst.simplepos.controllers;

import com.polylvst.simplepos.domain.dtos.CreateProductRequest;
import com.polylvst.simplepos.domain.dtos.ProductDto;
import com.polylvst.simplepos.domain.entities.Product;
import com.polylvst.simplepos.mappers.ProductMapper;
import com.polylvst.simplepos.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @Valid
            @RequestBody
            CreateProductRequest createProductRequest,
            @RequestAttribute UUID userId
        ) {
        Product productToCreate = productMapper.toEntity(createProductRequest);
        Product savedProduct = productService.createProduct(productToCreate, userId);
        return new ResponseEntity<>(
                productMapper.toDto(savedProduct),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable
            UUID id
    ) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(required = true)
            UUID id,
            @Valid
            @RequestBody
            CreateProductRequest createProductRequest,
            @RequestAttribute UUID userId
    ) {
        Product productToUpdate = productMapper.toEntity(createProductRequest);
        Product updatedProduct = productService.updateProduct(productToUpdate, id, userId);
        return new ResponseEntity<>(
                productMapper.toDto(updatedProduct),
                HttpStatus.OK
        );
    }
}
