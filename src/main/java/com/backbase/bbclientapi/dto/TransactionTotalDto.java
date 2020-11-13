package com.backbase.bbclientapi.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionTotalDto {

    private BigDecimal total;
}
