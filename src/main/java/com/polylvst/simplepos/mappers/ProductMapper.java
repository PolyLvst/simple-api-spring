package com.polylvst.simplepos.mappers;

import com.polylvst.simplepos.domain.dtos.CreateProductRequest;
import com.polylvst.simplepos.domain.dtos.ProductDto;
import com.polylvst.simplepos.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductDto toDto(Product product);
    Product toEntity(CreateProductRequest createProductRequest);
}
