package com.test.prismocodeassessment.service;

import com.test.prismocodeassessment.exception.AccountNotFoundException;
import com.test.prismocodeassessment.exception.InvalidTransactionException;
import com.test.prismocodeassessment.model.Account;
import com.test.prismocodeassessment.model.OperationType;
import com.test.prismocodeassessment.model.Transaction;
import com.test.prismocodeassessment.model.TransactionRequest;
import com.test.prismocodeassessment.utility.TransactionCollection;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service
public class TransactionService {

    private final AccountService accountService;

    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Transaction createTransaction(TransactionRequest transactionRequest) {
        final Integer accountId = transactionRequest.getAccountId();
        final Integer operationTypeId = transactionRequest.getTransactionTypeId();
        Map<Integer, Transaction> transactionCollection = TransactionCollection.getTransactionCollection();
        AtomicInteger transCount = new AtomicInteger(transactionCollection.size());
        if (OperationType.isValidOperation(operationTypeId)) {
            try {
                Account account = accountService.getAccount(accountId);
                Transaction newTransaction = Transaction.of(transCount.incrementAndGet(), account.getAccountId(),
                        operationTypeId, transactionRequest.getAmount());
                transactionCollection.put(transCount.get(), newTransaction);
                return newTransaction;
            } catch (AccountNotFoundException ex) {
                log.error("Account with accountId {} not available. Transaction cancelled.", accountId);
                throw new InvalidTransactionException(String.format("Account with accountId %d not available. Transaction cancelled.", accountId));
            }
        } else {
            log.error("Operation type Id {} invalid. Transaction cancelled.", operationTypeId);
            throw new InvalidTransactionException(String.format("Operation type Id %d invalid. Transaction cancelled.", operationTypeId));
        }
    }
}
