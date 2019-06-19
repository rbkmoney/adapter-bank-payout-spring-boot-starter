package com.rbkmoney.adapter.bank.payout.spring.boot.starter.service;

import com.rbkmoney.adapter.bank.payout.spring.boot.starter.config.properties.TimerProperties;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.EntryStateModel;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.ExitStateModel;
import com.rbkmoney.damsel.base.Timer;
import com.rbkmoney.damsel.domain.Failure;
import com.rbkmoney.damsel.domain.TransactionInfo;
import com.rbkmoney.damsel.withdrawals.provider_adapter.*;
import com.rbkmoney.error.mapping.ErrorMapping;
import com.rbkmoney.java.damsel.utils.extractors.OptionsExtractors;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.rbkmoney.java.damsel.utils.extractors.OptionsExtractors.extractMaxTimePolling;

@RequiredArgsConstructor
public class IntentServiceImpl implements IntentService {

    private final ErrorMapping errorMapping;
    private final TimerProperties timerProperties;

    public Intent getFailure(ExitStateModel exitStateModel) {
        return Intent.finish(new FinishIntent(FinishStatus.failure(errorMapping.getFailureByCodeAndDescription(exitStateModel.getErrorCode(), exitStateModel.getErrorMessage()))));
    }

    public Intent getSuccess(ExitStateModel exitStateModel) {
        return Intent.finish(new FinishIntent(FinishStatus.success(new Success(new TransactionInfo().setId(exitStateModel.getNextState().getTrxInfo().getTrxId())))));
    }

    public Intent getSleep(ExitStateModel exitStateModel) {
        if (exitStateModel.getNextState().getMaxDateTimePolling().getEpochSecond() < Instant.now().getEpochSecond()) {
            return Intent.finish(new FinishIntent(FinishStatus.failure(new Failure())));
        }
        int timerPollingDelay = OptionsExtractors.extractPollingDelay(exitStateModel.getEntryStateModel().getOptions(), timerProperties.getPollingDelay());
        return Intent.sleep(new SleepIntent(new Timer(Timer.timeout(timerPollingDelay))));
    }

    public Instant getMaxDateTimeInstant(EntryStateModel entryStateModel) {
        int maxTimePolling = extractMaxTimePolling(entryStateModel.getOptions(), timerProperties.getMaxTimePolling());
        return Instant.now().plus(maxTimePolling, ChronoUnit.MINUTES);
    }
}
