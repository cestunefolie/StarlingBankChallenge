package com.starling.challenge.rest;

import com.starling.challenge.exception.StarlingApiException;
import com.starling.challenge.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static com.starling.challenge.rest.RestHelper.getHeaders;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
public class SavingGoalRestClient {
    private final RestTemplate restTemplate;

    public SavingGoalRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${saving.goal.service.url}")
    private String savingGoalUrl;
    @Value("${token}")
    private String token;

    public SavingGoals getSavingGoals(String accountUid) {
        String uri = savingGoalUrl + accountUid + "/savings-goals";
        ResponseEntity<SavingGoals> response  = this.restTemplate.exchange(uri, GET, new HttpEntity<>(null, getHeaders(token)), SavingGoals.class);
        if(response.getStatusCode() != OK) {
            throw new StarlingApiException("Got Error Code: " + response.getStatusCode());
        }
        return response.getBody();
    }

    public RoundUpResponse putIntoSavingGoal(String savingsGoalUid, String savingGoalName, BigDecimal amount, String currency, String accountUid) {
        String uid = UUID.randomUUID().toString();
        String uri = savingGoalUrl + accountUid + "/savings-goals/" + savingsGoalUid + "/add-money/" + uid;

        TopUp body = TopUp.builder()
                .amount(Amount.builder()
                        .currency(currency)
                        .minorUnits(amount)
                        .build())
                .build();

        ResponseEntity<Void> response  = this.restTemplate.exchange(uri, PUT, new HttpEntity<>(body, getHeaders(token)), Void.class);
        if(response.getStatusCode() != OK) {
            throw new StarlingApiException("Got Error Code: " + response.getStatusCode());
        }
        log.info("Money is successfully moved ");
        return RoundUpResponse.builder()
                .message("Money moved into saving goal")
                .amount(amount)
                .savingGoalName(savingGoalName).build();
    }

    public void createSavingGoal(Account account) {
        String uri = savingGoalUrl + account.getAccountUid() + "/savings-goals/";

        SavingGoal body = SavingGoal.builder()
                .name("Future Adventures") // todo hardcoded value
                .currency(account.getCurrency())
                .base64EncodedPhoto("string")
                .target(Amount.builder()
                        .currency(account.getCurrency())
                        .minorUnits(BigDecimal.valueOf(1000))
                        .build())
                .build();

        ResponseEntity<Void> response = this.restTemplate.exchange(uri, PUT, new HttpEntity<>(body, getHeaders(token)), Void.class);
        if(response.getStatusCode() != OK) {
            throw new StarlingApiException("Got Error Code: " + response.getStatusCode());
        }
    }

}
