package com.polylvst.simplepos.services.impl;

import com.polylvst.simplepos.domain.entities.Product;
import com.polylvst.simplepos.repositories.ProductRepository;
import com.polylvst.simplepos.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product, UUID userId) {
        product.setCreatedBy(userId);
        product.setUpdatedBy(userId);
        return productRepository.save(product);
    }

    @Override
    public void updateProductObj(Product product, UUID userId) {
        product.setUpdatedBy(userId);
        productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, UUID id, UUID userId) {
        Product currentProduct = findById(id);

        currentProduct.setUpdatedBy(userId);
        currentProduct.setName(product.getName());
        currentProduct.setStock(product.getStock());
        currentProduct.setPrice(product.getPrice());

        return productRepository.save(currentProduct);
    }

    @Override
    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with  id "+id));
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
