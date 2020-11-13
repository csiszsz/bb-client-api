package com.backbase.bbclientapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenBankTransaction {
    private String id;
    @JsonProperty("this_account")
    private ThisAccount thisAccount;

    @JsonProperty("other_account")
    private OtherAccount otherAccount;

    private Details details;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThisAccount {
        private String id;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherAccount {
        private String number;
        private Holder holder;
        private Metadata metadata;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Holder {
            private String name;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Metadata {
            @JsonProperty("image_URL")
            private String imageURL;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Details {
        private Value value;
        private String type;
        private String description;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Value {
            private BigDecimal amount;
            private Currency currency;
        }
    }


}
