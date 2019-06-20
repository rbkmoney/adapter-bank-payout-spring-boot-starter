package com.rbkmoney.adapter.bank.payout.spring.boot.starter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdapterState {
    private Step step;
    private Long maxTimePoolingMillis;
    private TransactionInfo trxInfo;
}
