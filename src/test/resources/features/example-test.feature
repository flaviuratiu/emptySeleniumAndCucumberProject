Feature: Run an example test
  Demo feature to validate the project structure

  Scenario: Open a url
    Given I open the homepage
    And I wait 1 second
    When I press the More information link
    Then I am redirected to "http://www.iana.org/domains/reserved"
