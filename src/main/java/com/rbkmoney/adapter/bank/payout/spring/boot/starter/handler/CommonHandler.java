package com.rbkmoney.adapter.bank.payout.spring.boot.starter.handler;

import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.EntryStateModel;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.ExitStateModel;

public interface CommonHandler<T extends EntryStateModel, X extends ExitStateModel> {
    boolean isHandle(T entryStateModel);

    X handle(T entryStateModel);
}
