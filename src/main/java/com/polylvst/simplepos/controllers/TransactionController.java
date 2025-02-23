package com.polylvst.simplepos.controllers;

import com.polylvst.simplepos.domain.dtos.CreateTransactionRequest;
import com.polylvst.simplepos.domain.dtos.CreateTransactionRequestDto;
import com.polylvst.simplepos.domain.dtos.TransactionDto;
import com.polylvst.simplepos.domain.entities.Transaction;
import com.polylvst.simplepos.mappers.TransactionMapper;
import com.polylvst.simplepos.services.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping("/reports")
    public ResponseEntity<List<TransactionDto>> listTransactions(
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end
    ) {
        List<TransactionDto> transactions = transactionService.listTransactions(start, end)
                .stream()
                .map(transaction -> transactionMapper.toDto(transaction))
                .toList();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping()
    public ResponseEntity<TransactionDto> createTransaction(
            @Valid
            @RequestBody
            CreateTransactionRequest createTransactionRequest,
            @RequestAttribute UUID userId
    ) {
        CreateTransactionRequestDto createTransactionRequestDto = transactionMapper.toCreateTransaction(createTransactionRequest);
        Transaction newTransaction = transactionService.createTransaction(createTransactionRequestDto, userId);
        return new ResponseEntity<>(
                transactionMapper.toDto(newTransaction),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{transactionId}/refund")
    public ResponseEntity<TransactionDto> refundTransaction(
            @PathVariable(required = true) UUID transactionId,
            @RequestAttribute UUID userId
    ) {
        Transaction refundedTransaction = transactionService.refundTransaction(transactionId, userId);
        return new ResponseEntity<>(
                transactionMapper.toDto(refundedTransaction),
                HttpStatus.OK
        );
    }
}
