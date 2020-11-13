package com.backbase.bbclientapi.model.mapper;

import com.backbase.bbclientapi.model.BackbaseTransaction;
import com.backbase.bbclientapi.model.OpenBankTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mappings({
            @Mapping(source = "thisAccount.id", target = "accountId"),
            @Mapping(source = "otherAccount.number", target = "counterpartyAccount"),
            @Mapping(source = "otherAccount.holder.name", target = "counterpartyName"),
            @Mapping(source = "otherAccount.metadata.imageURL", target = "counterpartyLogoPath"),
            @Mapping(source = "details.value.amount", target = "instructedAmount"),
            @Mapping(source = "details.value.currency", target = "instructedCurrency"),
            @Mapping(source = "details.value.amount", target = "transactionAmount"),
            @Mapping(source = "details.value.currency", target = "transactionCurrency"),
            @Mapping(source = "details.type", target = "transactionType"),
            @Mapping(source = "details.description", target = "description")
    })
    BackbaseTransaction openBankTransactionToBackbaseTransaction(OpenBankTransaction openBankTransaction);

    List<BackbaseTransaction> openBankTransactionsToBackbaseTransactions(List<OpenBankTransaction> openBankTransactionList);
}
