package com.test.prismocodeassessment.service;

import com.test.prismocodeassessment.exception.AccountNotFoundException;
import com.test.prismocodeassessment.model.Account;
import com.test.prismocodeassessment.model.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

    private static final String DOCUMENT_NUMBER1 = "1234567890";
    private static final String DOCUMENT_NUMBER2 = "9876543210";
    AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
    }

    @Test
    @DisplayName("A new account is created when account with document number not present in the collection")
    void testCreateNewAccountWhenNewAccountCreated() {
        Account expectedAccount1 = Account.of(1, DOCUMENT_NUMBER1);
        Account expectedAccount2 = Account.of(2, DOCUMENT_NUMBER2);
        Account actualAccount1 = accountService.createNewAccount(new AccountRequest(DOCUMENT_NUMBER1));
        assertEquals(expectedAccount1, actualAccount1);
        Account actualAccount2 = accountService.createNewAccount(new AccountRequest(DOCUMENT_NUMBER2));
        assertEquals(expectedAccount2, actualAccount2);
    }

    @Test
    @DisplayName("The existing account is returned when account with document number present in the collection")
    void testCreateNewAccountWhenAccountExists() {
        //First create the account
        accountService.createNewAccount(new AccountRequest(DOCUMENT_NUMBER1));
        Account expectedAccount = Account.of(1, DOCUMENT_NUMBER1);
        //Try to create the account with same data
        Account actualAccount = accountService.createNewAccount(new AccountRequest(DOCUMENT_NUMBER1));
        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    @DisplayName("AccountNotFoundException.class is thrown when no account found for a given account Id")
    void testGetAccountAccountWhenAccountDoesNotExist() {
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(1000));
    }

    @Test
    @DisplayName("Return Account when account with given accountId present in the collection")
    void testGetAccountWhenAccountExists() {
        //First create the account
        Account expectedAccount = accountService.createNewAccount(new AccountRequest(DOCUMENT_NUMBER1));
        //Try to fetch the account with the acc
        Account actualAccount = accountService.getAccount(expectedAccount.getAccountId());
        assertEquals(expectedAccount, actualAccount);
    }
}