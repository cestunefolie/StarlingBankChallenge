package com.starling.challenge.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@JsonDeserialize(builder = RoundUpResponse.RoundUpResponseBuilder.class)
public class RoundUpResponse {
    private String message;
    private BigDecimal amount;
    private String savingGoalName;

    @JsonPOJOBuilder(withPrefix = "")
    public static class RoundUpResponseBuilder {
    }
}
