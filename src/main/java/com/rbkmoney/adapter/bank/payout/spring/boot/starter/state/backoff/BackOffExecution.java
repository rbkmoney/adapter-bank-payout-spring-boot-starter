package com.rbkmoney.adapter.bank.payout.spring.boot.starter.state.backoff;

@FunctionalInterface
public interface BackOffExecution {
    Long nextBackOff();
}
