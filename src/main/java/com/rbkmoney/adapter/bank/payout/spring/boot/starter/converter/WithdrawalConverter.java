package com.rbkmoney.adapter.bank.payout.spring.boot.starter.converter;

import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Withdrawal;

import java.util.Map;

public interface WithdrawalConverter<T> {

    T convert(Withdrawal withdrawal, Value state, Map<String, String> options);

}
