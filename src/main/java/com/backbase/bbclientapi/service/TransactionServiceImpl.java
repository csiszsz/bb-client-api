package com.backbase.bbclientapi.service;

import com.backbase.bbclientapi.client.OpenBankClient;
import com.backbase.bbclientapi.exception.TransactionNotFoundException;
import com.backbase.bbclientapi.model.BackbaseTransaction;
import com.backbase.bbclientapi.model.OpenBankTransactions;
import com.backbase.bbclientapi.model.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        List<BackbaseTransaction> backbaseTransactions = list().stream()
                .filter(backbaseTransaction -> backbaseTransaction.getTransactionType().equals(type))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(backbaseTransactions)) {
            String message = String.format("Could not find transaction with type: '%s'", type);
            log.debug(message);
            throw new TransactionNotFoundException(message);
        }
        return backbaseTransactions;
    }

    @Override
    public BigDecimal total(String type) {
        return filterByType(type).stream()
                .map(BackbaseTransaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
