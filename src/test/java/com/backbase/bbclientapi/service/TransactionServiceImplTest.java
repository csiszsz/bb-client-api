package com.backbase.bbclientapi.service;

import com.backbase.bbclientapi.client.OpenBankClient;
import com.backbase.bbclientapi.exception.TransactionNotFoundException;
import com.backbase.bbclientapi.model.BackbaseTransaction;
import com.backbase.bbclientapi.model.OpenBankTransaction;
import com.backbase.bbclientapi.model.OpenBankTransactions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionServiceImplTest {

    @Mock
    OpenBankClient openBankClient;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        OpenBankTransactions openBankTransactions = new OpenBankTransactions();

        OpenBankTransaction openBankTransaction1 = OpenBankTransaction.builder()
                .id("1")
                .thisAccount(OpenBankTransaction.ThisAccount.builder().id("acc1").build())
                .otherAccount(OpenBankTransaction.OtherAccount.builder()
                        .holder(OpenBankTransaction.OtherAccount.Holder.builder().name("client1").build())
                        .build())
                .details(OpenBankTransaction.Details.builder()
                        .type("SEPA")
                        .value(OpenBankTransaction.Details.Value.builder()
                                .amount(new BigDecimal(10))
                                .build())
                        .build())
                .build();

        OpenBankTransaction openBankTransaction2 = OpenBankTransaction.builder()
                .id("1")
                .thisAccount(OpenBankTransaction.ThisAccount.builder().id("acc2").build())
                .otherAccount(OpenBankTransaction.OtherAccount.builder()
                        .holder(OpenBankTransaction.OtherAccount.Holder.builder().name("client2").build())
                        .build())
                .details(OpenBankTransaction.Details.builder()
                        .type("SEPA")
                        .value(OpenBankTransaction.Details.Value.builder()
                                .amount(new BigDecimal(100))
                                .build())
                        .build())
                .build();

        OpenBankTransaction openBankTransaction3 = OpenBankTransaction.builder()
                .id("1")
                .thisAccount(OpenBankTransaction.ThisAccount.builder().id("acc3").build())
                .otherAccount(OpenBankTransaction.OtherAccount.builder()
                        .holder(OpenBankTransaction.OtherAccount.Holder.builder().name("client3").build())
                        .build())
                .details(OpenBankTransaction.Details.builder()
                        .type("CASH")
                        .value(OpenBankTransaction.Details.Value.builder()
                                .amount(new BigDecimal(1000))
                                .build())
                        .build())
                .build();

        openBankTransactions.setTransactions(Arrays.asList(openBankTransaction1, openBankTransaction2, openBankTransaction3));

        when(openBankClient.consumeApi()).thenReturn(openBankTransactions);
    }

    @Test
    void shouldReturnAllTransactions() {
        List<BackbaseTransaction> transactions = transactionService.list();

        assertEquals(3, transactions.size());

        assertEquals("client1", transactions.get(0).getCounterpartyName());
        assertEquals("client2", transactions.get(1).getCounterpartyName());
        assertEquals("client3", transactions.get(2).getCounterpartyName());
        assertEquals(new BigDecimal(10), transactions.get(0).getTransactionAmount());
        assertEquals(new BigDecimal(100), transactions.get(1).getTransactionAmount());
        assertEquals(new BigDecimal(1000), transactions.get(2).getTransactionAmount());
    }

    @Test
    void shouldReturnTransactionsWithTypeSepa() {
        List<BackbaseTransaction> transactions = transactionService.filterByType("SEPA");

        assertEquals(2, transactions.size());
        assertEquals("SEPA", transactions.get(0).getTransactionType());
        assertEquals("SEPA", transactions.get(1).getTransactionType());
    }

    @Test
    void shouldThrowTransactionNotFoundExceptionFilteringByUnkonwn() {
        assertThrows(TransactionNotFoundException.class, () -> transactionService.filterByType("Unknown"));
    }

    @Test
    void totalReturnTotalAmountForTransactionTypeSepa() {
        BigDecimal total = transactionService.total("SEPA");

        assertEquals(new BigDecimal(110), total);
    }

    @Test
    void shouldThrowTransactionNotFoundExceptionTotalByUnkonwn() {
        assertThrows(TransactionNotFoundException.class, () -> transactionService.total("unknown"));
    }
}