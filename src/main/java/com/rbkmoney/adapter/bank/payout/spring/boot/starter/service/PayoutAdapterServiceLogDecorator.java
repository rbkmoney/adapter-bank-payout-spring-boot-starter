package com.rbkmoney.adapter.bank.payout.spring.boot.starter.service;

import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.damsel.withdrawals.provider_adapter.AdapterSrv;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Callback;
import com.rbkmoney.damsel.withdrawals.provider_adapter.CallbackResult;
import com.rbkmoney.damsel.withdrawals.provider_adapter.GetQuoteFailure;
import com.rbkmoney.damsel.withdrawals.provider_adapter.GetQuoteParams;
import com.rbkmoney.damsel.withdrawals.provider_adapter.ProcessResult;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Quote;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Withdrawal;
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
    public ProcessResult processWithdrawal(Withdrawal withdrawal, Value state, Map<String, String> options)
            throws TException {
        String withdrawalId = withdrawal.getId();
        log.info("processWithdrawal: start with withdrawalId {}", withdrawalId);
        try {
            ProcessResult processResult = payoutAdapterService.processWithdrawal(withdrawal, state, options);
            log.info("processWithdrawal: finish {} with withdrawalId {}", processResult, withdrawalId);
            return processResult;
        } catch (Exception ex) {
            String message = "Exception in processWithdrawal with withdrawalId " + withdrawalId;
            logMessage(ex, message);
            throw ex;
        }
    }

    @Override
    public Quote getQuote(GetQuoteParams getQuoteParams, Map<String, String> map) throws GetQuoteFailure, TException {
        String withdrawalId = getQuoteParams.getIdempotencyId();
        log.info("getQuote: start with withdrawalId {}", withdrawalId);
        try {
            Quote quote = payoutAdapterService.getQuote(getQuoteParams, map);
            log.info("getQuote: finish {} with withdrawalId {}", quote, withdrawalId);
            return quote;
        } catch (Exception ex) {
            String message = "Exception in getQuote with withdrawalId " + withdrawalId;
            logMessage(ex, message);
            throw ex;
        }
    }

    @Override
    public CallbackResult handleCallback(Callback callback, Withdrawal withdrawal, Value value, Map<String, String> map)
            throws TException {
        String withdrawalId = withdrawal.getId();
        log.info("handleCallback: start with withdrawalId {}", withdrawalId);
        try {
            CallbackResult callbackResult = payoutAdapterService.handleCallback(callback, withdrawal, value, map);
            log.info("handleCallback: finish {} with withdrawalId {}", callbackResult, withdrawalId);
            return callbackResult;
        } catch (Exception ex) {
            String message = "Exception in handleCallback with withdrawalId " + withdrawalId;
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
