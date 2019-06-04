package com.rbkmoney.adapter.bank.payout.spring.boot.starter.processor;

public interface ResultProcessor<T, R> {

    R process(T t);

}
