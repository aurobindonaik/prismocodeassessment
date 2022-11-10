package com.test.prismocodeassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotNull
    Integer accountId;

    @NotNull
    Integer transactionTypeId;

    @NotNull
    Double amount;

    public static TransactionRequest of(Integer accountId, Integer transactionTypeId, Double amount) {
        return new TransactionRequest(accountId, transactionTypeId, amount);
    }
}
