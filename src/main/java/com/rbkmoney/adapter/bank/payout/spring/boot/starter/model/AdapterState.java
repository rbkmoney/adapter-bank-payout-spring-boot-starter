package com.rbkmoney.adapter.bank.payout.spring.boot.starter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(value = "start_date_time_polling")
    private Instant startDateTimePolling;
    @JsonProperty(value = "max_date_time_polling")
    private Instant maxDateTimePolling;

    private TransactionInfo trxInfo;
}
