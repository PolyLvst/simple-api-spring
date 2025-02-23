package com.polylvst.simplepos.services.impl;

import com.polylvst.simplepos.domain.dtos.CreateTransactionRequestDto;
import com.polylvst.simplepos.domain.entities.Product;
import com.polylvst.simplepos.domain.entities.Transaction;
import com.polylvst.simplepos.domain.entities.User;
import com.polylvst.simplepos.repositories.TransactionRepository;
import com.polylvst.simplepos.services.ProductService;
import com.polylvst.simplepos.services.TransactionService;
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
        Transaction newTransaction = new Transaction();

        UUID productId = transactionRequest.getProductId();
        Integer quantity = transactionRequest.getQuantity();

        Product getProduct = productService.findById(productId);
        Integer stock = getProduct.getStock();
        if (quantity > stock) {
            throw new IllegalStateException("Cannot buy more than stock amount left: "+stock);
        }
        BigDecimal totalPrice = calculateCost(quantity, getProduct.getPrice());

        newTransaction.setProduct(getProduct);
        newTransaction.setQuantity(transactionRequest.getQuantity());
        newTransaction.setTotalPrice(totalPrice);
        newTransaction.setRefunded(false);
        newTransaction.setCreatedBy(user.getId());
        newTransaction.setCashier(user);

        getProduct.setStock(stock - quantity);
        productService.createProduct(getProduct);
        transactionRepository.save(newTransaction);
        return newTransaction;
    }

    public BigDecimal calculateCost(Integer quantity, BigDecimal itemPrice)
    {
        return itemPrice.multiply(BigDecimal.valueOf(quantity));
    }

}