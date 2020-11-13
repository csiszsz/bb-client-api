package com.backbase.bbclientapi.client;

import com.backbase.bbclientapi.model.OpenBankTransactions;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log
public class OpenBankClient {

    private static final String OPEN_BANK_API_ROOT_URL = "https://apisandbox.openbankproject.com/obp/v1.2.1";
    private static final String TRANSACTION_LIST_URL = "/banks/rbs/accounts/savings-kids-john/public/transactions";
    private RestTemplate restTemplate = new RestTemplate();

    public OpenBankTransactions consumeApi() {
        ResponseEntity<OpenBankTransactions> openBankTransactionsResponseEntity = restTemplate.getForEntity(OPEN_BANK_API_ROOT_URL + TRANSACTION_LIST_URL, OpenBankTransactions.class);

        log.info(openBankTransactionsResponseEntity.toString());

        return openBankTransactionsResponseEntity.getBody();
    }
}
