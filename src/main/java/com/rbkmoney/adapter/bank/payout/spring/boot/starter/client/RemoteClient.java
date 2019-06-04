package com.rbkmoney.adapter.bank.payout.spring.boot.starter.client;

public interface RemoteClient<T, R> {

    R payout(T request);

}
