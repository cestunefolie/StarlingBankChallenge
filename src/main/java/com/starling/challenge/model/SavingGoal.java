package com.starling.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = SavingGoal.SavingGoalBuilder.class)
public class SavingGoal {
    private String savingsGoalUid;
    private String currency;
    private String name;
    private Amount target;
    private String base64EncodedPhoto;

    @JsonPOJOBuilder(withPrefix = "")
    public static class SavingGoalBuilder {
    }
}
