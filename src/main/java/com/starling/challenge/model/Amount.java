package com.starling.challenge.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;

@Getter
@Builder
@JsonDeserialize(builder = Amount.AmountBuilder.class)
public class Amount {
    private String currency;
    private BigDecimal minorUnits;

    @JsonPOJOBuilder(withPrefix = "")
    public static class AmountBuilder {
    }

    public BigDecimal getCashAmount() {
        if (Objects.isNull(minorUnits))
            return ZERO;
        return minorUnits.movePointLeft(2);
    }
}
