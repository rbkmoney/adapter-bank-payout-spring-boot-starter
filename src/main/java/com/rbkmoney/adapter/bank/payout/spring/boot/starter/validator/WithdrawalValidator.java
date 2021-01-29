package com.rbkmoney.adapter.bank.payout.spring.boot.starter.validator;

import com.rbkmoney.adapter.bank.payout.spring.boot.starter.exception.ValidationException;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.damsel.withdrawals.provider_adapter.Withdrawal;
import java.util.Map;

public interface WithdrawalValidator {
    void validate(Withdrawal withdrawal, Value state, Map<String, String> options) throws ValidationException;
}
