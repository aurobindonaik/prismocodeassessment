package com.test.prismocodeassessment.utility;

import com.test.prismocodeassessment.model.Transaction;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class TransactionCollection {
    public static Map<Integer, Transaction> transactionCollection = new HashMap<>();

    public static Map<Integer, Transaction> getTransactionCollection() {
        return transactionCollection;
    }
}
