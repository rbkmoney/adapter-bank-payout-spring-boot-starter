package com.rbkmoney.adapter.bank.payout.spring.boot.starter.state.backoff;


import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.AdapterState;


import java.util.Map;

public class BackOffUtils {
    public static int prepareNextPollingInterval(AdapterState adapterState, Map<String, String> options) {
        ExponentialBackOff exponentialBackOff = new ExponentialBackOff(adapterState, options);
        return exponentialBackOff.start().nextBackOff().intValue();
    }
}
