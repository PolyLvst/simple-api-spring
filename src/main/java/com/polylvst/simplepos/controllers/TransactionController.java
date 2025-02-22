package com.polylvst.simplepos.controllers;

import com.polylvst.simplepos.domain.dtos.TransactionDto;
import com.polylvst.simplepos.mappers.TransactionMapper;
import com.polylvst.simplepos.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping("/reports")
    public ResponseEntity<List<TransactionDto>> listTransactions() {
        List<TransactionDto> transactions = transactionService.listTransactions()
                .stream()
                .map(transaction -> transactionMapper.toDto(transaction))
                .toList();
        return ResponseEntity.ok(transactions);
    }
}
