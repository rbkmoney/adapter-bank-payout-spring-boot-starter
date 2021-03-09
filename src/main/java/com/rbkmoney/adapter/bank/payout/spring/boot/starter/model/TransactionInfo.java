package com.rbkmoney.adapter.bank.payout.spring.boot.starter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInfo {
    private String trxId;
    private Map<String, String> trxExtra;
}
