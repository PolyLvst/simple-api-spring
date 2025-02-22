package com.polylvst.simplepos.repositories;

import com.polylvst.simplepos.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );
}
