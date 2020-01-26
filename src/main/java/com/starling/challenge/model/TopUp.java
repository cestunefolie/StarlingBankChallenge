package com.starling.challenge.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopUp {
    private Amount amount;
}
