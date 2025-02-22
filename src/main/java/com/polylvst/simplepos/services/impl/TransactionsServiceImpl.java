package com.polylvst.simplepos.services.impl;

import com.polylvst.simplepos.domain.entities.Transaction;
import com.polylvst.simplepos.repositories.TransactionRepository;
import com.polylvst.simplepos.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> listTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> listTransactions(LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByCreatedAtBetween(start, end);
    }
}
