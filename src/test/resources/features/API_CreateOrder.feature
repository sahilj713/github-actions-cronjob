Feature: Create Order flow
  As a user I want to verify create order API workflow

@API
Scenario: Verify Create order API
  When user hit login API and store token
  And user creates order
  Then user should see same order at All orders