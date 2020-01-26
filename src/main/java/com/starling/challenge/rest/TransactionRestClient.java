package com.starling.challenge.rest;

import com.starling.challenge.exception.StarlingApiException;
import com.starling.challenge.model.Account;
import com.starling.challenge.model.Feed;
import com.starling.challenge.model.Feeds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.starling.challenge.rest.RestHelper.getHeaders;
import static com.starling.challenge.util.DateHelper.formatLocalDateTime;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
public class TransactionRestClient {

    private final RestTemplate restTemplate;

    public TransactionRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${transaction.service.url}")
    private String transactionsInfoUrl;
    @Value("${token}")
    private String token;

    public List<Feed> getTransactions(Account account) {
        String uri = transactionsInfoUrl + account.getAccountUid() + "/category/" + account.getDefaultCategory() + "/transactions-between";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekAgo = now.minusWeeks(1);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri)
                .queryParam("minTransactionTimestamp", formatLocalDateTime(weekAgo))
                .queryParam("maxTransactionTimestamp", formatLocalDateTime(now));
        log.info("Getting transaction for account {} for previous week", account.getAccountUid());
        ResponseEntity<Feeds> response  = this.restTemplate.exchange(builder.build().toUriString(), GET, new HttpEntity<>(null, getHeaders(token)), Feeds.class);
        if(response.getStatusCode() != OK) {
            throw new StarlingApiException("Got Error Code: " + response.getStatusCode());
        }
        return ofNullable(response.getBody())
                .map(Feeds::getFeedItems)
                .orElseGet(Collections::emptyList);
    }

}
