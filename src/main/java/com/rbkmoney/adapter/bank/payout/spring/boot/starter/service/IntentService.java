package com.rbkmoney.adapter.bank.payout.spring.boot.starter.service;

import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.EntryStateModel;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.ExitStateModel;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Intent;

public interface IntentService {
    Intent getFailureByCode(ExitStateModel exitStateModel);
    Intent getFailureByCodeAndDesc(ExitStateModel exitStateModel);
    Intent getSuccess(ExitStateModel exitStateModel);
    Intent getSleep(ExitStateModel exitStateModel);
    Long getMaxDateTimeInstant(EntryStateModel entryStateModel);
}
