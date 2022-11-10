package com.test.prismocodeassessment.controller;

import com.test.prismocodeassessment.exception.InvalidTransactionException;
import com.test.prismocodeassessment.model.Transaction;
import com.test.prismocodeassessment.model.TransactionRequest;
import com.test.prismocodeassessment.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        log.info(transactionRequest);
        Transaction newTransaction = transactionService.createTransaction(transactionRequest);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleInvalidTransaction(InvalidTransactionException invalidTransaction) {
        return invalidTransaction.getMessage();
    }
}
