package com.rbkmoney.adapter.bank.payout.spring.boot.starter.utils;

import com.rbkmoney.damsel.cds.ExpDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardDateConverter {

    public static String getYearFromExpDate(ExpDate expDate) {
        return String.format("%1$02d", expDate.getYear() % 100);
    }

    public static String getYearFromShort(short year) {
        return String.format("%1$02d", year % 100);
    }

    public static String getMonthFromExpDate(ExpDate expDate) {
        return String.format("%02d", expDate.getMonth());
    }

    public static String getMonthFromByte(byte month) {
        return String.format("%02d", month);
    }

}
