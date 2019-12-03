package com.rbkmoney.adapter.bank.payout.spring.boot.starter.flow;

import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.EntryStateModel;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.ExitStateModel;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.Step;

public interface StepResolver<T extends EntryStateModel, X extends ExitStateModel> {
    Step resolveEntry(T entryStateModel);

    Step resolveExit(X exitStateModel);
}
