package com.rbkmoney.adapter.bank.payout.spring.boot.starter.model;

import com.rbkmoney.adapter.common.model.PollingInfo;
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
    // TODO: backward compatibility
    private Long maxTimePoolingMillis;

    private Instant maxDateTimePolling;

    private TransactionInfo trxInfo;

    private PollingInfo pollingInfo;
}
