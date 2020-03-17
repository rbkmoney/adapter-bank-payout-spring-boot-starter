package com.rbkmoney.adapter.bank.payout.spring.boot.starter.handler;

import com.rbkmoney.damsel.withdrawals.provider_adapter.GetQuoteParams;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Quote;

import java.util.Map;

public interface GetQuoteHandler {
    Quote handle(GetQuoteParams getQuoteParams, Map<String, String> map);
}
