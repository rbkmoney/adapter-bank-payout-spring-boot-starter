package com.rbkmoney.adapter.bank.payout.spring.boot.starter.service;

import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.damsel.withdrawals.provider_adapter.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

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
            logMessage(ex, message);
            throw ex;
        }
    }

    @Override
    public Quote getQuote(GetQuoteParams getQuoteParams, Map<String, String> map) throws GetQuoteFailure, TException {
        String withdrawalId = getQuoteParams.getIdempotencyId();
        log.info("processWithdrawal: start with withdrawalId {}", withdrawalId);
        try {
            Quote quote = payoutAdapterService.getQuote(getQuoteParams, map);
            log.info("processWithdrawal: finish {} with withdrawalId {}", quote, withdrawalId);
            return quote;
        } catch (Exception ex) {
            String message = "Exception in getQuote with withdrawalId " + withdrawalId;
            logMessage(ex, message);
            throw ex;
        }
    }

    private void logMessage(Exception ex, String message) {
        if (isUndefinedResultOrUnavailable(ex)) {
            log.warn(message, ex);
        } else {
            log.error(message, ex);
        }
    }
}
