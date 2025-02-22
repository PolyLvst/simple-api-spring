package com.polylvst.simplepos.services;

import com.polylvst.simplepos.domain.entities.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    List<Transaction> listTransactions();
    List<Transaction> listTransactions(LocalDateTime start, LocalDateTime end);
}
