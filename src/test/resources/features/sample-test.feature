@google
Feature: Search on google

  As a user
  I want to perform a search using google search engine
  So that I can search for what I want

  Scenario: : Open google
    Given I open browser and navigate to "?tenant=qaintegration#/login"
    When I type "svalaparla@equalexperts.com" in input field of type="email"
    And I type "Test@1234" in input field of type="password"
    And I click on input field of type="submit"


