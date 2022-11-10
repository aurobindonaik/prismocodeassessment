package com.test.prismocodeassessment.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Test documentNumber must not be null")
    void testDocumentNumberIsNotNull() {
        AccountRequest request = new AccountRequest(null);
        Set<ConstraintViolation<AccountRequest>> violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<AccountRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("documentNumber");
        assertThat(violation.getMessage()).isEqualTo("must not be null");

        request = new AccountRequest("1234567890");
        violations = validator.validate(request);
        assertThat(violations.isEmpty()).isTrue();
    }
}