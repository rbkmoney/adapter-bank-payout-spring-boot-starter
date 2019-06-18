package com.rbkmoney.adapter.bank.payout.spring.boot.starter.model;

import lombok.Data;

import java.util.Map;

@Data
public class EntryStateModel {
    private String withdrawalId;
    private Long amount;
    private String currencyCode;
    private String pan;
    private Map<String, String> options;

    private AdapterState state;
}
