package com.test.prismocodeassessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.prismocodeassessment.exception.AccountNotFoundException;
import com.test.prismocodeassessment.model.Account;
import com.test.prismocodeassessment.model.AccountRequest;
import com.test.prismocodeassessment.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private static final String DOCUMENT_NUMBER = "1234567890";
    @Mock
    AccountService accountService;

    @InjectMocks
    private AccountController controller;

    private ObjectMapper mapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("openAccount gives bad Operation with incorrect request")
    void testOpenAccountGivesBadRequestError() throws Exception {
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("openAccount returns created account with correct request")
    void testOpenAccountReturnsCreatedAccount() throws Exception {
        AccountRequest accountRequest = new AccountRequest(DOCUMENT_NUMBER);
        when(accountService.createNewAccount(accountRequest))
                .thenReturn(Account.of(1, DOCUMENT_NUMBER));
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
        verify(accountService).createNewAccount(accountRequest);
    }

    @Test
    @DisplayName("getAccount gives NO_CONTENT when no account exists with the given accountId")
    void testGetAccountGivesNoContentWhenAccountDoesNotExist() throws Exception {
        when(accountService.getAccount(1000))
                .thenThrow(AccountNotFoundException.class);

        mockMvc.perform(get("/accounts/1000"))
                .andExpect(status().isNoContent())
                .andDo(print());
        verify(accountService).getAccount(1000);
    }

    @Test
    @DisplayName("getAccount gives NO_CONTENT when no account exists with the given accountId")
    void testGetAccountReturnsExistingAccount() throws Exception {
        when(accountService.getAccount(1))
                .thenReturn(Account.of(1, DOCUMENT_NUMBER));
        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId", is(1)))
                .andExpect(jsonPath("$.documentNumber", is(DOCUMENT_NUMBER)))
                .andDo(print());
        verify(accountService).getAccount(1);
    }
}