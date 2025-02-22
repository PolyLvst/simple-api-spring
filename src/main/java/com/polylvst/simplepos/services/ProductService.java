package com.polylvst.simplepos.services;

import com.polylvst.simplepos.domain.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> listProducts();
    Product createProduct(Product product);
    Product findById(UUID id);
}
