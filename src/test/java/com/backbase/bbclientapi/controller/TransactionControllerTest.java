package com.backbase.bbclientapi.controller;

import com.backbase.bbclientapi.exception.TransactionNotFoundException;
import com.backbase.bbclientapi.model.BackbaseTransaction;
import com.backbase.bbclientapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TransactionControllerTest {

    @MockBean
    TransactionService transactionService;

    @Autowired
    private MockMvc mvc;


    @WithMockUser(value = "spring")
    @Test
    void listShouldReturnOk() throws Exception {
        BackbaseTransaction backbaseTransaction1 = new BackbaseTransaction();
        backbaseTransaction1.setId("123");

        BackbaseTransaction backbaseTransaction2 = new BackbaseTransaction();
        backbaseTransaction2.setId("456");

        given(transactionService.list()).willReturn(Arrays.asList(backbaseTransaction1, backbaseTransaction2));

        mvc.perform(get("/transactions/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['transactions']", hasSize(2)))
                .andExpect(jsonPath("$['transactions'][0].id", is("123")))
                .andExpect(jsonPath("$['transactions'][1].id", is("456")));
    }

    @WithMockUser(value = "spring")
    @Test
    void filterShouldReturnOk() throws Exception {
        BackbaseTransaction backbaseTransaction1 = new BackbaseTransaction();
        backbaseTransaction1.setId("123");
        backbaseTransaction1.setTransactionType("CASH");

        given(transactionService.filterByType("CASH")).willReturn(Collections.singletonList(backbaseTransaction1));

        mvc.perform(get("/transactions/filter")
                .queryParam("type", "CASH")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['transactions']", hasSize(1)))
                .andExpect(jsonPath("$['transactions'][0].id", is("123")))
                .andExpect(jsonPath("$['transactions'][0].transactionType", is("CASH")));
    }

    @WithMockUser(value = "spring")
    @Test
    void filterShouldReturnNotFound() throws Exception {

        given(transactionService.filterByType("CASH")).willThrow(new TransactionNotFoundException("Couldn't find transaction"));

        mvc.perform(get("/transactions/filter")
                .queryParam("type", "CASH")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "spring")
    @Test
    void totalShouldReturnOk() throws Exception {
        given(transactionService.total("SEPA")).willReturn(new BigDecimal(42));

        mvc.perform(get("/transactions/total/SEPA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['total']", is(42)));
    }

    @WithMockUser(value = "spring")
    @Test
    void totalShouldReturnNotFound() throws Exception {
        given(transactionService.total("SEPA")).willThrow(new TransactionNotFoundException("Couldn't find transaction"));

        mvc.perform(get("/transactions/total/SEPA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}