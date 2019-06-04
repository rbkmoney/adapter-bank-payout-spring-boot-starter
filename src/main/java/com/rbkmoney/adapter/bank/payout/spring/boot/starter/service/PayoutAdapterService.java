package com.rbkmoney.adapter.bank.payout.spring.boot.starter.service;


import com.rbkmoney.adapter.bank.payout.spring.boot.starter.client.RemoteClient;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.converter.WithdrawalConverter;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.processor.ResultProcessor;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.damsel.withdrawals.provider_adapter.AdapterSrv;
import com.rbkmoney.damsel.withdrawals.provider_adapter.ProcessResult;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Withdrawal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayoutAdapterService<R, T> implements AdapterSrv.Iface {

    private final WithdrawalConverter<T> converter;
    private final RemoteClient<T, R> client;
    private final ResultProcessor<R, ProcessResult> resultProcessorChain;

    @Override
    public ProcessResult processWithdrawal(Withdrawal withdrawal, Value state, Map<String, String> options) {
        T request = converter.convert(withdrawal, state, options);
        R response = client.payout(request);
        return resultProcessorChain.process(response);
    }
}