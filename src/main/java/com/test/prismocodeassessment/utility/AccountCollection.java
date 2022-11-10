package com.test.prismocodeassessment.utility;

import com.test.prismocodeassessment.model.Account;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class AccountCollection {
    public static Map<Integer, Account> accountCollection = new HashMap<>();

    public static Map<Integer, Account> getAccountCollection() {
        return accountCollection;
    }
}
