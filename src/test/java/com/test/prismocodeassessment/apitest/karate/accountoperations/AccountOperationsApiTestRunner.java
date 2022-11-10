package com.test.prismocodeassessment.apitest.karate.accountoperations;

import com.intuit.karate.junit5.Karate;

class AccountOperationsApiTestRunner {
    @Karate.Test
    Karate testAccountOperationsFeature() {
        return Karate.run().relativeTo(getClass());
    }
}
