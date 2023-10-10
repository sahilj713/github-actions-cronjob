Feature: Create Order flow
  As a user I want to verify create order workflow

  Background: Login to app
    Given User navigates to app home page

  @test
  Scenario: Verify create order
    When User login with "Jill.Taylor" and "112233" creds
    Then User should be on "Select a Facility" page
    When User selects "Baptist Medical Center" facility
    And User clicks on "Proceed" button
    And User clicks on "Create Order" icon
    And User enters details for create order
    And User selects "English" language
    And User attach file for "Attach Face Sheet"
    And User attach file for "Attach Prescription"
    And User clicks on "Create Order" button
    Then User should be on "All Orders" page