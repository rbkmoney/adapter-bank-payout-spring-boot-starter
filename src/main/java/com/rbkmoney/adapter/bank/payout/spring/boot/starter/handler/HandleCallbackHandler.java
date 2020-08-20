package com.rbkmoney.adapter.bank.payout.spring.boot.starter.handler;

import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Callback;
import com.rbkmoney.damsel.withdrawals.provider_adapter.CallbackResult;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Withdrawal;

import java.util.Map;

public interface HandleCallbackHandler {
    CallbackResult handleCallback(Callback callback, Withdrawal withdrawal, Value value, Map<String, String> map);
}
