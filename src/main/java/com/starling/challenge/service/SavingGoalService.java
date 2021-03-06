package com.starling.challenge.service;

import com.starling.challenge.exception.MissingSavingGoalException;
import com.starling.challenge.model.Account;
import com.starling.challenge.model.SavingGoal;
import com.starling.challenge.model.SavingGoals;
import com.starling.challenge.rest.RoundUpResponse;
import com.starling.challenge.rest.SavingGoalRestClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@AllArgsConstructor
public class SavingGoalService {

    private SavingGoalRestClient savingGoalRestClient;

    public RoundUpResponse moveToSavingGoal(BigDecimal amount, Account account) {
        log.info("Moving {} into Saving Goal", amount.movePointLeft(2));
        SavingGoals savingGoals = savingGoalRestClient.getSavingGoals(account.getAccountUid());
        if (isNull(savingGoals.getSavingsGoalList()) || savingGoals.getSavingsGoalList().isEmpty()) {
            log.info("No saving goal found for account: {} creating new one", account.getAccountUid());
            savingGoalRestClient.createSavingGoal(account);
            log.info("Trying to retrieve saving goal after creating new one");
            savingGoals = savingGoalRestClient.getSavingGoals(account.getAccountUid());
        }
        SavingGoal savingGoal = getSavingGoal(savingGoals, account);
        log.info("Moving money into saving goal: {}", savingGoal.getName());
        return savingGoalRestClient.putIntoSavingGoal(savingGoal.getSavingsGoalUid(), savingGoal.getName(), amount, account.getCurrency(), account.getAccountUid());
    }


    SavingGoal getSavingGoal(SavingGoals savingGoals, Account account) {
        return ofNullable(savingGoals)
                .map(SavingGoals::getSavingsGoalList)
                .flatMap(s -> s.stream().findFirst())
                .orElseThrow(() -> new MissingSavingGoalException(format("No Saving Goals retrieved for account %s", account.getAccountUid())));
    }
}
