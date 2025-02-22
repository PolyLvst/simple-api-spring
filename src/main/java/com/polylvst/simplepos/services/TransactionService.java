package com.polylvst.simplepos.services;

import com.polylvst.simplepos.domain.entities.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> listTransactions();
}
