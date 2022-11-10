package com.test.prismocodeassessment.service;

import com.test.prismocodeassessment.exception.AccountNotFoundException;
import com.test.prismocodeassessment.model.Account;
import com.test.prismocodeassessment.model.AccountRequest;
import com.test.prismocodeassessment.utility.AccountCollection;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AccountService {
    public Account createNewAccount(AccountRequest accountRequest) {
        final String documentNumber = accountRequest.getDocumentNumber();
        Map<Integer, Account> accountCollection = AccountCollection.getAccountCollection();
        AtomicInteger accCount = new AtomicInteger(accountCollection.size());
        Optional<Account> optionalAcc = accountCollection.values().stream()
                .filter(docNumber -> documentNumber.equals(docNumber.getDocumentNumber()))
                .findFirst();
        if(optionalAcc.isPresent()) {
            return optionalAcc.get();
        } else{
            final Account account = Account.of(accCount.incrementAndGet(), documentNumber);
            accountCollection.put(accCount.get(), account);
            return account;
        }
    }

    public Account getAccount(Integer accountId) {
        Map<Integer, Account> accountCollection = AccountCollection.getAccountCollection();
        if(accountCollection.containsKey(accountId)) {
            return accountCollection.get(accountId);
        } else{
            throw new AccountNotFoundException(String.format("No Account found with the given account id: %d", accountId));
        }
    }
}
