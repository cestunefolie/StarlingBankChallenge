package com.starling.challenge.rest;

import com.starling.challenge.exception.MissingAccountException;
import com.starling.challenge.exception.StarlingApiException;
import com.starling.challenge.model.Account;
import com.starling.challenge.model.Accounts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.starling.challenge.rest.RestHelper.getHeaders;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
public class AccountRestClient {
    private final RestTemplate restTemplate;

    public AccountRestClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${account.service.url}")
    private String accountInfoUrl;
    @Value("${token}")
    private String token;

    public Account getAccounts() {
        log.info("Getting accounts");

        ResponseEntity<Accounts> response  = this.restTemplate.exchange(accountInfoUrl, GET, new HttpEntity<>(null, getHeaders(token)), Accounts.class);
        if(response.getStatusCode() != OK) {
            throw new StarlingApiException("Got Error Code: " + response.getStatusCode());
        }
        return ofNullable(response.getBody())
                .map(Accounts::getAccounts)
                .map(accounts -> accounts.get(0))
                .orElseThrow(() -> new MissingAccountException("No Account found"));

    }

}
