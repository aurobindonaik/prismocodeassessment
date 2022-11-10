package com.test.prismocodeassessment.model;

import java.util.Arrays;

public enum OperationType {
    PURCHASE(1, "PURCHASE"),
    INSTALLMENT(2, "INSTALLMENT"),
    WITHDRAWAL(3, "WITHDRAWAL"),
    PAYMENT(4, "PAYMENT"),
    ;
    private Integer id;
    private String description;

    OperationType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static boolean isValidOperation(Integer operationId) {
        return Arrays.stream(OperationType.values())
                .anyMatch(value -> value.id.equals(operationId));
    }
}
