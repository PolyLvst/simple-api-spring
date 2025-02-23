package com.polylvst.simplepos.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User cashier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private boolean refunded;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private UUID createdBy;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private UUID updatedBy;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return refunded == that.refunded && Objects.equals(id, that.id) && Objects.equals(cashier, that.cashier) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(createdAt, that.createdAt) && Objects.equals(createdBy, that.createdBy) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cashier, product, quantity, totalPrice, refunded, createdAt, createdBy, updatedAt, updatedBy);
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
