package com.polylvst.simplepos.domain.dtos;

import com.polylvst.simplepos.domain.entities.Product;
import com.polylvst.simplepos.domain.entities.User;
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
    private boolean isRefunded;
    private LocalDateTime createdAt;
}
