package com.starling.challenge.util;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.UP;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class BigDecimalUtil {
    public static BigDecimal getChange(BigDecimal value){
        if(isNull(value))
            return ZERO;
        return value.setScale(0, UP).subtract(value);
    }

    public static boolean isGreaterThanZero(BigDecimal amount) {
        return nonNull(amount) && amount.compareTo(ZERO) > 0;
    }
}
