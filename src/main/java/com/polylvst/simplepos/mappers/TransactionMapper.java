package com.polylvst.simplepos.mappers;

import com.polylvst.simplepos.domain.dtos.TransactionDto;
import com.polylvst.simplepos.domain.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {
    TransactionDto toDto(Transaction transaction);
}
