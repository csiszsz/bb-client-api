package com.backbase.bbclientapi.controller;

import com.backbase.bbclientapi.dto.TransactionDto;
import com.backbase.bbclientapi.dto.TransactionListDto;
import com.backbase.bbclientapi.dto.TransactionTotalDto;
import com.backbase.bbclientapi.dto.mapper.TransactionDtoMapper;
import com.backbase.bbclientapi.model.BackbaseTransaction;
import com.backbase.bbclientapi.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@Api(description = "Set of endpoints for retrieving transactions")
@Slf4j
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/list")
    @ApiOperation(value = "Endpoint for listing transactions")
    public TransactionListDto list() {
        List<BackbaseTransaction> backbaseTransactions = transactionService.list();

        log.info("Retrieving transactions: {}", backbaseTransactions);

        List<TransactionDto> transactionDtos = TransactionDtoMapper.INSTANCE.backBaseTransactiosToTransactionListDtos(backbaseTransactions);
        return TransactionListDto.builder().transactions(transactionDtos).build();
    }

    @GetMapping("/filter")
    @ApiOperation(value = "Endpoint for filtering transactions by type")
    public TransactionListDto filter(
            @RequestParam
            @ApiParam(value = "Type of transaction", required = true)
                    String type) {
        List<BackbaseTransaction> backbaseTransactions = transactionService.filterByType(type);

        log.info("Retrieving transactions: {}", backbaseTransactions);

        List<TransactionDto> transactionDtos = TransactionDtoMapper.INSTANCE.backBaseTransactiosToTransactionListDtos(backbaseTransactions);
        return TransactionListDto.builder().transactions(transactionDtos).build();
    }

    @GetMapping("/total/{type}")
    @ApiOperation(value = "Endpoint for calculating the total amount for the transaction type")
    public TransactionTotalDto total(
            @PathVariable
            @ApiParam(value = "Type of transaction", required = true)
                    String type) {
        BigDecimal total = transactionService.total(type);

        log.info("Total amount: {}", total);

        return TransactionTotalDto.builder().total(total).build();
    }
}
