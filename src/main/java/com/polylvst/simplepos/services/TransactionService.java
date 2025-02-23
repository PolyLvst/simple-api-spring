package com.polylvst.simplepos.services;

import com.polylvst.simplepos.domain.dtos.CreateTransactionRequestDto;
import com.polylvst.simplepos.domain.entities.Transaction;
import com.polylvst.simplepos.domain.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    List<Transaction> listTransactions();
    List<Transaction> listTransactions(LocalDateTime start, LocalDateTime end);
    Transaction createTransaction(CreateTransactionRequestDto createTransactionRequestDto, User user);
    Transaction findById(UUID transactionId);
    Transaction refundTransaction(UUID transactionId, User user);
}
