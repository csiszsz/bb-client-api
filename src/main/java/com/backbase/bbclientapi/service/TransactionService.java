package com.backbase.bbclientapi.service;

import com.backbase.bbclientapi.model.BackbaseTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<BackbaseTransaction> list();

    List<BackbaseTransaction> filterByType(String type);

    BigDecimal total(String type);
}
