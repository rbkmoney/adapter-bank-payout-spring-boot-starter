package com.rbkmoney.adapter.bank.payout.spring.boot.starter.state.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.adapter.bank.payout.spring.boot.starter.model.AdapterState;
import com.rbkmoney.adapter.common.state.deserializer.DeserializationException;
import com.rbkmoney.adapter.common.state.deserializer.Deserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
public class AdapterStateDeserializer implements Deserializer<AdapterState> {

    private final ObjectMapper mapper;

    public AdapterState read(byte[] data) {
        if (data == null) {
            return new AdapterState();
        } else {
            try {
                return this.getMapper().readValue(data, AdapterState.class);
            } catch (IOException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
    }

    public AdapterState read(String data) {
        throw new DeserializationException("Deserialization not supported");
    }

    public ObjectMapper getMapper() {
        return this.mapper;
    }

}

