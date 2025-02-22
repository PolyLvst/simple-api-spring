package com.polylvst.simplepos.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "product name is required")
    private String name;

    @NotNull(message = "price is required")
    private BigDecimal price;

    @NotNull(message = "stock is required")
    private Integer stock;
}
