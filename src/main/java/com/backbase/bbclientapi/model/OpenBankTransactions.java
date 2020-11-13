package com.backbase.bbclientapi.model;

import lombok.Data;

import java.util.List;

@Data
public class OpenBankTransactions {
    private List<OpenBankTransaction> transactions;
}
