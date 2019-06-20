package com.rbkmoney.adapter.bank.payout.spring.boot.starter.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Map;

@Data
public class EntryStateModel {
    private String withdrawalId;
    private Long amount;
    private String currencyCode;
    @ToStringExclude
    private String pan;
    private Map<String, String> options;

    private AdapterState state;
}
