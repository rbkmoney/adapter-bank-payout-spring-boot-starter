package com.rbkmoney.adapter.bank.payout.spring.boot.starter.state.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.AdapterState;
import com.rbkmoney.adapter.common.state.serializer.StateSerializer;

public class AdapterStateSerializer extends StateSerializer<AdapterState> {
    public AdapterStateSerializer(ObjectMapper mapper) {
        super(mapper);
    }
}
