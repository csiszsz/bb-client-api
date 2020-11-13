package com.backbase.bbclientapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TransactionDto {
    private String id;
    private String accountId;
    private String counterpartyAccount;
    private String counterpartyName;
    private String counterpartyLogoPath;
    private BigDecimal instructedAmount;
    private String instructedCurrency;
    private BigDecimal transactionAmount;
    private Currency transactionCurrency;
    private String transactionType;
    private String description;
}
