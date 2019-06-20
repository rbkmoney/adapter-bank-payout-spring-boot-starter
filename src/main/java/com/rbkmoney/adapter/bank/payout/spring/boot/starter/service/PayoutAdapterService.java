package com.rbkmoney.adapter.bank.payout.spring.boot.starter.service;


import com.rbkmoney.adapter.bank.payout.spring.boot.starter.converter.ExitStateToProcessResultConverter;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.converter.WithdrawalToEntryStateConverter;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.flow.StepResolver;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.handler.CommonHandler;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.EntryStateModel;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.ExitStateModel;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.validator.WithdrawalValidator;
import com.rbkmoney.adapter.common.exception.UnsupportedMethodException;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.damsel.withdrawals.provider_adapter.AdapterSrv;
import com.rbkmoney.damsel.withdrawals.provider_adapter.ProcessResult;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Withdrawal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PayoutAdapterService<T extends EntryStateModel, X extends ExitStateModel> implements AdapterSrv.Iface {

    private final WithdrawalToEntryStateConverter<T> withdrawalToEntryStateConverter;
    private final ExitStateToProcessResultConverter<X> exitStateToProcessResultConverter;
    private final List<CommonHandler<T, X>> handlers;
    private final StepResolver<T, X> resolver;
    private final WithdrawalValidator validator;

    @Override
    public ProcessResult processWithdrawal(Withdrawal withdrawal, Value state, Map<String, String> options) throws TException {
        validator.validate(withdrawal, state, options);
        T entryStateModel = withdrawalToEntryStateConverter.convert(withdrawal, state, options);
        entryStateModel.getState().setStep(resolver.resolveEntry(entryStateModel));
        X exitStateModel = handlers.stream()
                .filter(h -> h.isHandle(entryStateModel))
                .findFirst()
                .orElseThrow(UnsupportedMethodException::new)
                .handle(entryStateModel);
        exitStateModel.getNextState().setStep(resolver.resolveExit(exitStateModel));
        return exitStateToProcessResultConverter.convert(exitStateModel);
    }
}
