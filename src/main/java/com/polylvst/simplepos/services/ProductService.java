package com.polylvst.simplepos.services;

import com.polylvst.simplepos.domain.entities.Product;
import com.polylvst.simplepos.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> listProducts();
    Product createProduct(Product product, User user);
    Product updateProduct(Product product, User user);
    Product findById(UUID id);
}
