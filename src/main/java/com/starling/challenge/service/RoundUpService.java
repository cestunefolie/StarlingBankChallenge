package com.starling.challenge.service;

import com.starling.challenge.model.Account;
import com.starling.challenge.model.Amount;
import com.starling.challenge.model.Feed;
import com.starling.challenge.rest.AccountRestClient;
import com.starling.challenge.rest.RoundUpResponse;
import com.starling.challenge.rest.TransactionRestClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.starling.challenge.util.BigDecimalUtil.getChange;
import static com.starling.challenge.util.BigDecimalUtil.isGreaterThanZero;
import static java.math.BigDecimal.ZERO;

@Slf4j
@Service
@AllArgsConstructor
public class RoundUpService {

    private AccountRestClient accountRestClient;
    private TransactionRestClient transactionRestClient;
    private SavingGoalService savingGoalService;

    public RoundUpResponse roundUp() {
        Account account = accountRestClient.getAccounts();
        log.info("Got account : {}", account);
        List<Feed> transactions = transactionRestClient.getTransactions(account);
        BigDecimal amount = roundUp(transactions);
        if (isGreaterThanZero(amount)) {
            return savingGoalService.moveToSavingGoal(amount, account);
        } else {
            return RoundUpResponse.builder()
                    .message("No money available to move into saving goal")
                    .build();
        }
    }

    static BigDecimal roundUp(List<Feed> transactions) {
        return transactions.stream()
                .filter(Objects::nonNull)
                //.filter(f -> f.getDirection().equals("IN")) // not specified in requirements but filter on Incoming only
                .map(Feed::getAmount)
                .filter(Objects::nonNull)
                .map(Amount::getCashAmount)
                .reduce(ZERO, (v1, v2) -> v1.add(getChange(v2)))
                .movePointRight(2);
    }
}
