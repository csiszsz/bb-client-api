package com.backbase.bbclientapi.service;

import com.backbase.bbclientapi.client.OpenBankClient;
import com.backbase.bbclientapi.model.BackbaseTransaction;
import com.backbase.bbclientapi.model.OpenBankTransactions;
import com.backbase.bbclientapi.model.mapper.TransactionMapper;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class TransactionServiceImpl implements TransactionService {

    private OpenBankClient openBankClient;

    public TransactionServiceImpl(OpenBankClient openBankClient) {
        this.openBankClient = openBankClient;
    }

    @Override
    public List<BackbaseTransaction> list() {
        OpenBankTransactions openBankTransactions = openBankClient.consumeApi();

        return TransactionMapper.INSTANCE.openBankTransactionsToBackbaseTransactions(openBankTransactions.getTransactions());
    }

    @Override
    public List<BackbaseTransaction> filterByType(String type) {
        return list().stream()
                .filter(backbaseTransaction -> backbaseTransaction.getTransactionType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal total(String type) {
        return filterByType(type).stream()
                .map(BackbaseTransaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
