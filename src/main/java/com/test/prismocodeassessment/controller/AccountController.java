package com.test.prismocodeassessment.controller;

import com.test.prismocodeassessment.exception.AccountNotFoundException;
import com.test.prismocodeassessment.model.Account;
import com.test.prismocodeassessment.model.AccountRequest;
import com.test.prismocodeassessment.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> openAccount(@Valid @RequestBody AccountRequest accountRequest) {
        log.info(accountRequest);
        Account newAccount = accountService.createNewAccount(accountRequest);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountId") Integer accountId) {
        try {
            Account account = accountService.getAccount(accountId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (AccountNotFoundException ex) {
            log.error("No Account found for accountId {}", accountId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
