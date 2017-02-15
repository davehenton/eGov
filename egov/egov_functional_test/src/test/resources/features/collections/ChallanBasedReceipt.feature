Feature: Create/Collect Challan Based Receipt

As a registered user of the system
I am able to Create/Collect Challan Based Receipt


  @Collections  @Sanity @Smoke
  Scenario Outline: System should be able to Create Challan

    Given juniorAssistant logs in
   # And he chooses to create Challan
    And user will select the required screen as "Create Challan"
    And he enters challan details
    And he create challan and closes acknowledgement
    Then user will be notified by "successfully"
    And current user logs out

    And seniorAssistant logs in
    And chooses to act upon the above challan
    And he validate the challan
    Then user will be notified by "Validated"

    And he search for challan receipt
    And he search for challan number
    And he pay using <paymentMethod>
    And user closes the acknowledgement
    And current user logs out

    Examples:
      |paymentMethod|
      |cash         |
      |cheque       |
      |dd           |















