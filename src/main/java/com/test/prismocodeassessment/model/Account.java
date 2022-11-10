package com.test.prismocodeassessment.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Account {
    Integer accountId;
    String documentNumber;

    public static Account of(int accountId, String documentNumber) {
        return new Account(accountId, documentNumber);
    }
}
