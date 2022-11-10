package com.test.prismocodeassessment.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.test.prismocodeassessment.model.OperationType.INSTALLMENT;
import static com.test.prismocodeassessment.model.OperationType.PURCHASE;
import static com.test.prismocodeassessment.model.OperationType.WITHDRAWAL;
import static com.test.prismocodeassessment.model.OperationType.PAYMENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class OperationTypeTest {

    @Test
    @DisplayName("Verify the isValidOperation returns correct value")
    void isValidOperation() {
        assertTrue(OperationType.isValidOperation(PURCHASE.getId()));
        assertTrue(OperationType.isValidOperation(INSTALLMENT.getId()));
        assertTrue(OperationType.isValidOperation(WITHDRAWAL.getId()));
        assertTrue(OperationType.isValidOperation(PAYMENT.getId()));
        assertFalse(OperationType.isValidOperation(5));
    }
}