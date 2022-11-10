package com.test.prismocodeassessment.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Transaction {
    Integer transactionId;
    Integer accountId;
    Integer transactionTypeId;
    Double amount;

    public static Transaction of(Integer transactionId, Integer accountId, Integer transactionTypeId, Double amount) {
        return new Transaction(transactionId, accountId, transactionTypeId, amount);
    }
}
