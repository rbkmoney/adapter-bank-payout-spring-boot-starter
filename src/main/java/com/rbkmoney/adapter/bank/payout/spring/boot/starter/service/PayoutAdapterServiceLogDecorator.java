package com.rbkmoney.adapter.bank.payout.spring.boot.starter.service;

import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.damsel.withdrawals.provider_adapter.AdapterSrv;
import com.rbkmoney.damsel.withdrawals.provider_adapter.ProcessResult;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Withdrawal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.rbkmoney.java.damsel.utils.verification.ProxyProviderVerification.isUndefinedResultOrUnavailable;

@Slf4j
@RequiredArgsConstructor
public class PayoutAdapterServiceLogDecorator implements AdapterSrv.Iface {

    private final AdapterSrv.Iface payoutAdapterService;

    @Override
    public ProcessResult processWithdrawal(Withdrawal withdrawal, Value state, Map<String, String> options) throws TException {
        String withdrawalId = withdrawal.getId();
        log.info("processWithdrawal: start with withdrawalId {}", withdrawalId);
        try {
            ProcessResult processResult = payoutAdapterService.processWithdrawal(withdrawal, state, options);
            log.info("processWithdrawal: finish {} with withdrawalId {}", processResult, withdrawalId);
            return processResult;
        } catch (Exception ex) {
            String message = "Exception in processPayment with withdrawalId " + withdrawalId;
            if (isUndefinedResultOrUnavailable(ex)) {
                log.warn(message, ex);
            } else {
                log.error(message, ex);
            }
            throw ex;
        }
    }
}
