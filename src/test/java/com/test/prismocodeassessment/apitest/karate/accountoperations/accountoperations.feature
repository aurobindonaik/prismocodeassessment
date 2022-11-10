Feature: Account Operation Feature

  Background:
    * url baseUrl = 'http://localhost:8080'
    * def account =
    """{
        "documentNumber": "12345678900"
        } """
    * def transaction =
    """{
        "accountId": 1,
        "operationTypeId": 4,
        "amount": 123.45
        }
         """
    
  Scenario: Test the POST /accounts
    Given path '/accounts'
    And request account
    When method POST
    Then status 201
    And assert response != null
    And match responseType == 'json'
    And print 'Response is: ', response
    And match response.data.accountId == 1
    And match response.data.documentNumber == "12345678900"

#  Scenario: Test GET /accounts/:accountId
#    Given path operationsBase + '/accounts/12345678900'
#    When method GET
#    Then status 200
#    And match responseType == 'json'
#    And print 'Response is: ', response
#    And match response.data.accountId == 1
#    And match response.data.documentNumber == "12345678900"
#
#  Scenario: Test the POST /transactions
#    Given path operationsBase + '/transactions'
#    And request transaction
#    When method POST
#    Then status 201
#    And assert response != null
#    And match responseType == 'json'
#    And print 'Response is: ', response
#    And match response.data.transactionId == 1
#    And match response.data.accountId == 1
#    And match response.data.operationTypeId == 4
#    And match response.data.amount == 123.45
