package com.backbase.bbclientapi.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
public class BackbaseTransaction {
    private String id;
    private String accountId;
    private String counterpartyAccount;
    private String counterpartyName;
    private String counterpartyLogoPath;
    private BigDecimal instructedAmount;
    private Currency instructedCurrency;
    private BigDecimal transactionAmount;
    private Currency transactionCurrency;
    private String transactionType;
    private String description;
}
