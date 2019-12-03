package com.rbkmoney.adapter.bank.payout.spring.boot.starter.state.backoff;

import com.rbkmoney.adapter.common.state.backoff.BackOffExecution;

public interface BackOff {
    BackOffExecution start();
}
