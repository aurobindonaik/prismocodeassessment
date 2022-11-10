package com.test.prismocodeassessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.prismocodeassessment.exception.InvalidTransactionException;
import com.test.prismocodeassessment.model.OperationType;
import com.test.prismocodeassessment.model.Transaction;
import com.test.prismocodeassessment.model.TransactionRequest;
import com.test.prismocodeassessment.service.AccountService;
import com.test.prismocodeassessment.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerITTest {
    @MockBean
    private TransactionService transactionService;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("createTransaction gives bad request with invalid request")
    void testCreateTransactionGivesBadRequestError() throws Exception {
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(TransactionRequest.of(null, null, null)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("createTransaction gives error message for invalid accountId")
    void testCreateTransactionGivesErrorMessageForInvalidAccountId() throws Exception {
        InvalidTransactionException invalidTransactionException = new InvalidTransactionException("Account with accountId 1000 not available. Transaction cancelled.");
        int nonExistingAccountId = 1000;
        final TransactionRequest transactionRequest = TransactionRequest.of(nonExistingAccountId, OperationType.PAYMENT.getId(), 3.5);
        when(transactionService.createTransaction(transactionRequest)).thenThrow(invalidTransactionException);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transactionRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Account with accountId 1000 not available. Transaction cancelled."))
                .andDo(print());
        verify(transactionService).createTransaction(transactionRequest);
    }

    @Test
    @DisplayName("createTransaction gives error message for invalid operationTypeId")
    void testCreateTransactionGivesErrorMessageForInvalidOperationTypeId() throws Exception {
        InvalidTransactionException invalidTransactionException = new InvalidTransactionException("Operation type Id 7 invalid. Transaction cancelled.");
        int existingAccountId = 1;
        int nonExistingOperationTypeId = 5;
        final TransactionRequest transactionRequest = TransactionRequest.of(existingAccountId, nonExistingOperationTypeId, 3.5);
        when(transactionService.createTransaction(transactionRequest)).thenThrow(invalidTransactionException);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transactionRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Operation type Id 7 invalid. Transaction cancelled."))
                .andDo(print());
        verify(transactionService).createTransaction(transactionRequest);
    }

    @Test
    @DisplayName("createTransaction creates transaction for correct TransactionRequest")
    void testCreateTransactionForCorrectTransactionRequest() throws Exception {
        final TransactionRequest transactionRequest = TransactionRequest.of(1, OperationType.PAYMENT.getId(), 3.5);
        Transaction transaction = Transaction.of(1, 1, OperationType.PAYMENT.getId(), 3.5);
        when(transactionService.createTransaction(transactionRequest)).thenReturn(transaction);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transactionRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId", is(1)))
                .andExpect(jsonPath("$.accountId", is(1)))
                .andExpect(jsonPath("$.transactionTypeId", is(4)))
                .andExpect(jsonPath("$.amount", is(3.5)))
                .andDo(print());
        verify(transactionService).createTransaction(transactionRequest);
    }
}