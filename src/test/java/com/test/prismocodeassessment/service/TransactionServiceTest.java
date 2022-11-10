package com.test.prismocodeassessment.service;


import com.test.prismocodeassessment.exception.AccountNotFoundException;
import com.test.prismocodeassessment.exception.InvalidTransactionException;
import com.test.prismocodeassessment.model.Account;
import com.test.prismocodeassessment.model.OperationType;
import com.test.prismocodeassessment.model.Transaction;
import com.test.prismocodeassessment.model.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private static final Account ACCOUNT = Account.of(1, "1234567890");
    @Mock
    private AccountService accountService;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(accountService);
    }

    @Test
    @DisplayName("createTransaction throws InvalidTransactionException when account does not exist for given accountId")
    void testCreateTransactionThrowsExceptionWhenAccountDoesNotExists() {
        Integer nonExistingAccountId = 1000;
        when(accountService.getAccount(nonExistingAccountId)).thenThrow(AccountNotFoundException.class);
        TransactionRequest invalidTransactionRequest = TransactionRequest.of(nonExistingAccountId, 1, 3.5);
        assertThrows(InvalidTransactionException.class, () -> transactionService.createTransaction(invalidTransactionRequest));
        verify(accountService).getAccount(nonExistingAccountId);
    }

    @Test
    @DisplayName("createTransaction throws InvalidTransactionException when operationTypeId is invalid")
    void testCreateTransactionThrowsExceptionWhenInvalidOperationTypeId() {
        Integer existingAccountId = 1;
        Integer invalidOperationId = 5;
        TransactionRequest invalidTransactionRequest = TransactionRequest.of(existingAccountId, invalidOperationId, 3.5);
        assertThrows(InvalidTransactionException.class, () -> transactionService.createTransaction(invalidTransactionRequest));
        verify(accountService, never()).getAccount(existingAccountId);
    }

    @Test
    @DisplayName("createTransaction returns a transaction with a valid request")
    void testCreateTransactionReturnsTransactionWithValidTransactionRequest() {
        Integer existingAccountId = 1;
        Integer paymentTransactionOperationId = OperationType.PAYMENT.getId();
        Transaction expectedTransaction = Transaction.of(1, ACCOUNT.getAccountId(), paymentTransactionOperationId, 3.5);
        when(accountService.getAccount(existingAccountId)).thenReturn(ACCOUNT);
        TransactionRequest validTransactionRequest = TransactionRequest.of(existingAccountId, paymentTransactionOperationId, 3.5);
        Transaction actualTransaction = transactionService.createTransaction(validTransactionRequest);
        assertEquals(expectedTransaction, actualTransaction);
        verify(accountService).getAccount(existingAccountId);
    }
}