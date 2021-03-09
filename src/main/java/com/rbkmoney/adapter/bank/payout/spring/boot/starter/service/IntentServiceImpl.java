package com.rbkmoney.adapter.bank.payout.spring.boot.starter.service;

import com.rbkmoney.adapter.bank.payout.spring.boot.starter.config.properties.TimerProperties;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.EntryStateModel;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.ExitStateModel;
import com.rbkmoney.adapter.common.model.PollingInfo;
import com.rbkmoney.adapter.common.utils.times.ExponentialBackOffPollingService;
import com.rbkmoney.damsel.domain.TransactionInfo;
import com.rbkmoney.damsel.withdrawals.provider_adapter.FinishIntent;
import com.rbkmoney.damsel.withdrawals.provider_adapter.FinishStatus;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Intent;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Success;
import com.rbkmoney.error.mapping.ErrorMapping;
import com.rbkmoney.java.damsel.utils.creators.WithdrawalsProviderAdapterPackageCreators;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.rbkmoney.java.damsel.utils.extractors.OptionsExtractors.extractMaxTimePolling;

@RequiredArgsConstructor
public class IntentServiceImpl implements IntentService {

    private final ErrorMapping errorMapping;
    private final TimerProperties timerProperties;

    public Intent getFailureByCode(ExitStateModel exitStateModel) {
        return Intent
                .finish(new FinishIntent(FinishStatus.failure(errorMapping.mapFailure(exitStateModel.getErrorCode()))));
    }

    public Intent getFailureByCodeAndDesc(ExitStateModel exitStateModel) {
        return Intent.finish(new FinishIntent(FinishStatus
                .failure(errorMapping.mapFailure(exitStateModel.getErrorCode(), exitStateModel.getErrorMessage()))));
    }

    public Intent getSuccess(ExitStateModel exitStateModel) {
        com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.TransactionInfo trxInfo =
                exitStateModel.getNextState().getTrxInfo();
        Success success = new Success();
        success.setTrxInfo(new TransactionInfo()
                .setId(trxInfo.getTrxId())
                .setExtra(trxInfo.getTrxExtra()));
        return Intent.finish(new FinishIntent(FinishStatus.success(success)));
    }

    public Intent getSleep(ExitStateModel exitStateModel) {
        Instant maxDateTimePolling = exitStateModel.getNextState().getPollingInfo().getMaxDateTimePolling();
        if (maxDateTimePolling == null) {
            throw new IllegalArgumentException("Need to specify 'maxDateTimePolling' before sleep");
        }
        if (maxDateTimePolling.toEpochMilli() < Instant.now().toEpochMilli()) {
            return prepareFailureIntent();
        }
        int timerPollingDelay = computePollingInterval(exitStateModel);
        return WithdrawalsProviderAdapterPackageCreators.createIntentWithSleepIntent(timerPollingDelay);
    }

    private Intent prepareFailureIntent() {
        String code = "Sleep timeout";
        String reason = "Max time pool limit reached";
        return Intent.finish(new FinishIntent(FinishStatus.failure(errorMapping.mapFailure(code, reason))));
    }

    private int computePollingInterval(ExitStateModel exitStateModel) {
        ExponentialBackOffPollingService<PollingInfo> pollingService = new ExponentialBackOffPollingService<>();
        return pollingService.prepareNextPollingInterval(
                exitStateModel.getNextState().getPollingInfo(),
                exitStateModel.getEntryStateModel().getOptions()
        );
    }

    public Long getMaxDateTimeInstantMillis(EntryStateModel entryStateModel) {
        int maxTimePolling = extractMaxTimePolling(entryStateModel.getOptions(), timerProperties.getMaxTimePolling());
        return Instant.now().plus(maxTimePolling, ChronoUnit.MINUTES).toEpochMilli();
    }

    public Instant extractMaxDateTimeInstant(EntryStateModel entryStateModel) {
        int maxTimePolling = extractMaxTimePolling(entryStateModel.getOptions(), timerProperties.getMaxTimePolling());
        return Instant.now().plus(maxTimePolling, ChronoUnit.MINUTES);
    }
}
