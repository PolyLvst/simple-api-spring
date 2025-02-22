package com.polylvst.simplepos.mappers;

import com.polylvst.simplepos.domain.dtos.TransactionDto;
import com.polylvst.simplepos.domain.entities.Product;
import com.polylvst.simplepos.domain.entities.Transaction;
import com.polylvst.simplepos.domain.entities.User;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    @Mapping(target = "cashier", source = "cashier", qualifiedByName = "getCashierName")
    @Mapping(target = "product", source = "product", qualifiedByName = "getProductName")
    TransactionDto toDto(Transaction transaction);

    @Named("getCashierName")
    default String getCashierName(User user) {
        if (user != null) { return user.getUsername(); }
        return null;
    }

    @Named("getProductName")
    default String getProductName(Product product) {
        if (product != null){ return product.getName(); }
        return null;
    }
}
