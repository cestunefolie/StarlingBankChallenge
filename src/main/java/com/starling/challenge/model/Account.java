package com.starling.challenge.model;

import lombok.Data;

@Data
public class Account {
    private String accountUid;
    private String defaultCategory;
    private String currency;
    private String createdAt;

}
