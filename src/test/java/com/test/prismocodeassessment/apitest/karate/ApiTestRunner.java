package com.test.prismocodeassessment.apitest.karate;

import com.intuit.karate.junit5.Karate;

class ApiTestRunner {
    @Karate.Test
    Karate testAllFeatures() {
        return Karate.run().relativeTo(getClass());
    }
}
