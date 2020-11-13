package com.backbase.bbclientapi.dto.mapper;

import com.backbase.bbclientapi.dto.TransactionDto;
import com.backbase.bbclientapi.model.BackbaseTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionDtoMapper {
    TransactionDtoMapper INSTANCE = Mappers.getMapper(TransactionDtoMapper.class);

    List<TransactionDto> backBaseTransactiosToTransactionListDtos(List<BackbaseTransaction> backbaseTransactions);
}
