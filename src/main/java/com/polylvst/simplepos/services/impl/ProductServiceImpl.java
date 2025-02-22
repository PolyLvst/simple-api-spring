package com.polylvst.simplepos.services.impl;

import com.polylvst.simplepos.domain.entities.Product;
import com.polylvst.simplepos.repositories.ProductRepository;
import com.polylvst.simplepos.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) { return productRepository.save(product); }
}
