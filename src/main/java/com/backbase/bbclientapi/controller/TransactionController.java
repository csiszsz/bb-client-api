package com.backbase.bbclientapi.controller;

import com.backbase.bbclientapi.dto.TransactionDto;
import com.backbase.bbclientapi.dto.TransactionListDto;
import com.backbase.bbclientapi.dto.TransactionTotalDto;
import com.backbase.bbclientapi.dto.mapper.TransactionDtoMapper;
import com.backbase.bbclientapi.model.BackbaseTransaction;
import com.backbase.bbclientapi.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/list")
    public TransactionListDto list() {
        List<BackbaseTransaction> backbaseTransactions = transactionService.list();

        log.info("Retrieving transactions: {}", backbaseTransactions);

        List<TransactionDto> transactionDtos = TransactionDtoMapper.INSTANCE.backBaseTransactiosToTransactionListDtos(backbaseTransactions);
        return TransactionListDto.builder().transactions(transactionDtos).build();
    }

    @GetMapping("/filter")
    public TransactionListDto filter(@RequestParam String type) {
        List<BackbaseTransaction> backbaseTransactions = transactionService.filterByType(type);

        log.info("Retrieving transactions: {}", backbaseTransactions);

        List<TransactionDto> transactionDtos = TransactionDtoMapper.INSTANCE.backBaseTransactiosToTransactionListDtos(backbaseTransactions);
        return TransactionListDto.builder().transactions(transactionDtos).build();
    }

    @GetMapping("/total/{type}")
    public TransactionTotalDto total(@PathVariable String type) {
        BigDecimal total = transactionService.total(type);

        log.info("Total amount: {}", total);

        return TransactionTotalDto.builder().total(total).build();
    }
}
