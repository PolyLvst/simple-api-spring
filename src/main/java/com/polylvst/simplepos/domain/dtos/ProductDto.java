package com.polylvst.simplepos.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer stock;
}
