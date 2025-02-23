package com.polylvst.simplepos.services.impl;

import com.polylvst.simplepos.domain.dtos.CreateTransactionRequestDto;
import com.polylvst.simplepos.domain.entities.Product;
import com.polylvst.simplepos.domain.entities.Transaction;
import com.polylvst.simplepos.domain.entities.User;
import com.polylvst.simplepos.repositories.TransactionRepository;
import com.polylvst.simplepos.services.ProductService;
import com.polylvst.simplepos.services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductService productService;

    @Override
    public List<Transaction> listTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> listTransactions(LocalDateTime start, LocalDateTime end) {
        if (start == null) {
            start = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        }
        if (end == null) {
            end = LocalDateTime.now();
        }
        return transactionRepository.findByCreatedAtBetween(start, end);
    }

    @Override
    @Transactional
    public Transaction createTransaction(CreateTransactionRequestDto transactionRequest, User user) {
        UUID productId = transactionRequest.getProductId();
        Integer quantity = transactionRequest.getQuantity();

        Product getProduct = productService.findById(productId);
        Integer stock = getProduct.getStock();
        if (quantity > stock) {
            throw new IllegalStateException("Cannot buy more than stock amount left: "+stock);
        }
        BigDecimal totalPrice = calculateCost(quantity, getProduct.getPrice());

        Transaction newTransaction = Transaction.builder()
                .product(getProduct)
                .quantity(transactionRequest.getQuantity())
                .totalPrice(totalPrice)
                .isRefunded(false)
                .createdBy(user.getId())
                .updatedBy(user.getId())
                .cashier(user)
                .build();

        getProduct.setStock(stock - quantity);
        // Update product
        productService.createProduct(getProduct);
        transactionRepository.save(newTransaction);
        return newTransaction;
    }

    @Override
    public Transaction findById(UUID transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new EntityNotFoundException("Transaction not found with id "+transactionId));
    }

    @Override
    @Transactional
    public Transaction refundTransaction(UUID transactionId, User user) {
        Transaction transaction = findById(transactionId);

        UUID productId = transaction.getProduct().getId();
        Integer quantity = transaction.getQuantity();

        Product getProduct = productService.findById(productId);
        Integer stock = getProduct.getStock();

        transaction.setRefunded(true);
        transaction.setUpdatedBy(user.getId());

        getProduct.setStock(stock + quantity);
        // Update product
        productService.createProduct(getProduct);
        transactionRepository.save(transaction);
        return transaction;
    }

    public BigDecimal calculateCost(Integer quantity, BigDecimal itemPrice)
    {
        return itemPrice.multiply(BigDecimal.valueOf(quantity));
    }

}