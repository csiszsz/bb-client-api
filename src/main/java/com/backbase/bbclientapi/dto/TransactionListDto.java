package com.backbase.bbclientapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionListDto {

    private List<TransactionDto> transactions;
}
