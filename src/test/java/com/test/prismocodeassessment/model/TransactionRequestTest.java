package com.test.prismocodeassessment.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Test accountId must not be null")
    void testAccountIdIsNotNull() {
        TransactionRequest request = TransactionRequest.of(null, OperationType.INSTALLMENT.getId(), 3.5);
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<TransactionRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("accountId");
        assertThat(violation.getMessage()).isEqualTo("must not be null");
    }

    @Test
    @DisplayName("Test transactionTypeId must not be null")
    void testTransactionTypeIdIsNotNull() {
        TransactionRequest request = TransactionRequest.of(1, null, 3.5);
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<TransactionRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("transactionTypeId");
        assertThat(violation.getMessage()).isEqualTo("must not be null");
    }

    @Test
    @DisplayName("Test amount must not be null")
    void testAmountIsNotNull() {
        TransactionRequest request = TransactionRequest.of(1, OperationType.INSTALLMENT.getId(), null);
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<TransactionRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("amount");
        assertThat(violation.getMessage()).isEqualTo("must not be null");
    }

    @Test
    @DisplayName("Test valid TransactionRequest")
    void testValidTransactionRequest() {
        TransactionRequest request = TransactionRequest.of(1, OperationType.INSTALLMENT.getId(), 3.7);
        Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);
        assertThat(violations.isEmpty()).isTrue();
    }
}