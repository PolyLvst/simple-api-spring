package com.polylvst.simplepos.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private UUID id;
    private UserDto cashier;
    private ProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
    private boolean refunded;
    private LocalDateTime createdAt;
}
