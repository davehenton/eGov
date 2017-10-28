Feature: Renewal of trade license


    # Trade License Renewal #
  @Sanity @TradeLicense @LicenseRenewal @New
  Scenario Outline: Renewal of Trade License with legacy license

    Given TL_PHS_JA logs in
    And user will select the required screen as "Create Legacy License"
    And he enters old license number
    And he enters trade owner details of new license <tradeDetailsData>
    And he enters trade location details of new license <tradeLocationData>
    And he enters trade details of new license <tradeDetailsData1>
    And he enters fee details of legency trade license
    And he saves the application
    And he copies the license number and closes the acknowledgement
    And current user logs out

    When CSCUser logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Renew License"
    And he choose to renew trade license
    And he closes search screen
    And current user logs out


    When TL_PHS_JA logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Collect Fees"
    And he choose to payTax of applicationNumber
    And he chooses to act upon above application number
    And he forwards for approver TL_SI
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_SI logs in
    And he chooses to act upon above application number
    And he forwards for approver TL_Commissioner
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_ADM_Commissioner logs in
    And he chooses to act upon above application number
    And he approves application
    And he confirms to proceed
    And he closes acknowledgement page

    And user will select the required screen as "Search Trade License"
    And he search existing application number
    And he verifies the application status
    And user will be notified by "Active"
    And he verifies the License active
    And user will be notified by "YES"
    And he closes search screen
    And current user logs out

    Examples:
      | tradeDetailsData         | tradeLocationData           | tradeDetailsData1        |
      | ownerDetailsTradeLicense | locationDetailsTradeLicense | tradeDetailsTradeLicense |

#  @TradeLicense
  Scenario Outline: Renewal of TL -> collect fee -> forward to SI -> forward to Commissioner -> reject

    Given TL_PHS_JA logs in
    And user will select the required screen as "Create New License"
    And he enters trade owner details of new license <tradeDetailsData>
    And he enters trade location details of new license <tradeLocationData>
    And he enters trade details of new license <tradeDetailsData1>
    And he saves the application
    And he copy trade application number

    And user will select the required screen as "Search Trade License"
    And he search existing application number
    And he choose action "Collect Fees"
    And he choose to payTax of applicationNumber
    And he chooses to act upon above application number

    And he forwards for approver TL_SI
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_SI logs in
    And he chooses to act upon above application number
    And he forwards for approver TL_Commissioner
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_ADM_Commissioner logs in
    And he chooses to act upon above application number
    And he approves application
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_JA logs in
    And he chooses to act upon above application number
    And he generates the license certificate

    And user will select the required screen as "Search Trade License"
    And he search existing application number
    And he choose action "Generate Demand"
    And he generates demand
    And user will be notified by "successfully"
    And he choose action "Renew License"
    And he copy trade license number
    And he choose to renew trade license
    And he choose to search with license number
    And he choose action "Collect Fees"
    And he choose to payTax of applicationNumber
    And he chooses to act upon above application number
    And he forwards for approver TL_SI
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_SI logs in
    And he chooses to act upon above application number
    And he forwards for approver TL_Commissioner
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_ADM_Commissioner logs in
    And he chooses to act upon above application number
    And he rejects the application
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_JA logs in
    And he chooses to act upon above application number
    And he rejects the application
    And he confirms to proceed
    And he closes acknowledgement page
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he verifies the application status
    And user will be notified by "Cancelled"
    And he verifies the License active
    And user will be notified by "YES"
    And he closes search screen
    And current user logs out

    Examples:
      | tradeDetailsData         | tradeLocationData           | tradeDetailsData1        |
      | ownerDetailsTradeLicense | locationDetailsTradeLicense | tradeDetailsTradeLicense |

#  @TradeLicense
  Scenario Outline: Renewal of TL -> collect fee -> forward to SI
  -> change trade area and forward to Commissioner -> reject

    Given TL_PHS_JA logs in
    And user will select the required screen as "Create New License"
    And he enters trade owner details of new license <tradeDetailsData>
    And he enters trade location details of new license <tradeLocationData>
    And he enters trade details of new license <tradeDetailsData1>
    And he saves the application
    And he copy trade application number

    And user will select the required screen as "Search Trade License"
    And he search existing application number
    And he choose action "Collect Fees"
    And he choose to payTax of applicationNumber
    And he chooses to act upon above application number

    And he forwards for approver TL_SI
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_SI logs in
    And he chooses to act upon above application number
    And he changes trade area as "2000"
    And he forwards for approver TL_Commissioner
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_ADM_Commissioner logs in
    And he chooses to act upon above application number
    And he rejects the application
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_JA logs in
    And he chooses to act upon above application number
    And he rejects the application
    And he confirms to proceed
    And he closes acknowledgement page
    And user will select the required screen as "Search Trade License"
    And he search existing application number
    And he verifies the application status
    And user will be notified by "Cancelled"
    And he verifies the License active
    And user will be notified by "NO"
    And he closes search screen
    And current user logs out


    Examples:
      | tradeDetailsData         | tradeLocationData           | tradeDetailsData1        |
      | ownerDetailsTradeLicense | locationDetailsTradeLicense | tradeDetailsTradeLicense |


#  @Sanity @TradeLicense @LicenseRenewal
  Scenario Outline: Renewal of Trade License with legacy license and commissioner rejects it

    Given TL_PHS_JA logs in
    And user will select the required screen as "Create Legacy License"
    And he enters old license number
    And he enters trade owner details of new license <tradeDetailsData>
    And he enters trade location details of new license <tradeLocationData>
    And he enters trade details of new license <tradeDetailsData1>
    And he enters fee details of legency trade license
    And he saves the application
    And he copies the license number and closes the acknowledgement
    And current user logs out

    When CSCUser logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Renew License"
    And he choose to renew trade license
    And he closes search screen
    And current user logs out


    When TL_PHS_JA logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Collect Fees"
    And he choose to payTax of applicationNumber
    And he chooses to act upon above application number
    And he forwards for approver TL_SI
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_SI logs in
    And he chooses to act upon above application number
    And he forwards for approver TL_Commissioner
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_ADM_Commissioner logs in
    And he chooses to act upon above application number
    And he rejects the application
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_JA logs in
    And he chooses to act upon above application number
    And he rejects the application
    And he confirms to proceed
    And he closes acknowledgement page
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he verifies the application status
    And user will be notified by "Cancelled"
    And he verifies the License active
    And user will be notified by "YES"
    And he closes search screen
    And current user logs out

    Examples:
      | tradeDetailsData         | tradeLocationData           | tradeDetailsData1        |
      | ownerDetailsTradeLicense | locationDetailsTradeLicense | tradeDetailsTradeLicense |


  @Sanity @TradeLicense @LicenseRenewal
  Scenario Outline: Renewal of Trade License with legacy license and SI rejects it

    Given TL_PHS_JA logs in
    And user will select the required screen as "Create Legacy License"
    And he enters old license number
    And he enters trade owner details of new license <tradeDetailsData>
    And he enters trade location details of new license <tradeLocationData>
    And he enters trade details of new license <tradeDetailsData1>
    And he enters fee details of legency trade license
    And he saves the application
    And he copies the license number and closes the acknowledgement
    And current user logs out

    When CSCUser logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Renew License"
    And he choose to renew trade license
    And he closes search screen
    And current user logs out


    When TL_PHS_JA logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Collect Fees"
    And he choose to payTax of applicationNumber
    And he chooses to act upon above application number
    And he forwards for approver TL_SI
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_SI logs in
    And he chooses to act upon above application number
    And he rejects the application
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_JA logs in
    And he chooses to act upon above application number
    And he rejects the application
    And he confirms to proceed
    And he closes acknowledgement page
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he verifies the application status
    And user will be notified by "Cancelled"
    And he verifies the License active
    And user will be notified by "YES"
    And he closes search screen
    And current user logs out

    Examples:
      | tradeDetailsData         | tradeLocationData           | tradeDetailsData1        |
      | ownerDetailsTradeLicense | locationDetailsTradeLicense | tradeDetailsTradeLicense |


  @Sanity @TradeLicense @LicenseRenewal
  Scenario Outline: Renewal of Trade License with legacy license - JA rejects it before collection

    Given TL_PHS_JA logs in
    And user will select the required screen as "Create Legacy License"
    And he enters old license number
    And he enters trade owner details of new license <tradeDetailsData>
    And he enters trade location details of new license <tradeLocationData>
    And he enters trade details of new license <tradeDetailsData1>
    And he enters fee details of legency trade license
    And he saves the application
    And he copies the license number and closes the acknowledgement
    And current user logs out

    When CSCUser logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Renew License"
    And he choose to renew trade license
    And he closes search screen
    And current user logs out


    When TL_PHS_JA logs in
    And he chooses to act upon above application number
    And he cancel the application
    And he closes acknowledgement page
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he verifies the application status
    And user will be notified by "Cancelled"
    And he verifies the License active
    And user will be notified by "YES"
    And he closes search screen
    And current user logs out


    Examples:
      | tradeDetailsData         | tradeLocationData           | tradeDetailsData1        |
      | ownerDetailsTradeLicense | locationDetailsTradeLicense | tradeDetailsTradeLicense |

  @Sanity @TradeLicense @LicenseRenewal
  Scenario Outline: Renewal of Trade License with legacy license and second level fee collection

    Given TL_PHS_JA logs in
    And user will select the required screen as "Create Legacy License"
    And he enters old license number
    And he enters trade owner details of new license <tradeDetailsData>
    And he enters trade location details of new license <tradeLocationData>
    And he enters trade details of new license <tradeDetailsData1>
    And he enters fee details of legency trade license
    And he saves the application
    And he copies the license number and closes the acknowledgement
    And current user logs out

    When CSCUser logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Renew License"
    And he choose to renew trade license
    And he closes search screen
    And current user logs out


    When TL_PHS_JA logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Collect Fees"
    And he choose to payTax of applicationNumber
    And he chooses to act upon above application number
    And he forwards for approver TL_SI
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_SI logs in
    And he chooses to act upon above application number
    And he changes trade area as "1200"
    And he forwards for approver TL_Commissioner
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_ADM_Commissioner logs in
    And he chooses to act upon above application number
    And he approves application
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_JA logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Collect Fees"
    And he choose to payTax of applicationNumber

    And user will select the required screen as "Search Trade License"
    And he search existing application number
    And he verifies the application status
    And user will be notified by "Active"
    And he verifies the License active
    And user will be notified by "YES"
    And he closes search screen
    And current user logs out

    Examples:
      | tradeDetailsData         | tradeLocationData           | tradeDetailsData1        |
      | ownerDetailsTradeLicense | locationDetailsTradeLicense | tradeDetailsTradeLicense |

#  @Sanity @TradeLicense @LicenseRenewal
  Scenario Outline: Renewal of Trade License with legacy license and rejection before second level fee collection

    Given TL_PHS_JA logs in
    And user will select the required screen as "Create Legacy License"
    And he enters old license number
    And he enters trade owner details of new license <tradeDetailsData>
    And he enters trade location details of new license <tradeLocationData>
    And he enters trade details of new license <tradeDetailsData1>
    And he enters fee details of legency trade license
    And he saves the application
    And he copies the license number and closes the acknowledgement
    And current user logs out

    When CSCUser logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Renew License"
    And he choose to renew trade license
    And he closes search screen
    And current user logs out


    When TL_PHS_JA logs in
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he choose action "Collect Fees"
    And he choose to payTax of applicationNumber
    And he chooses to act upon above application number
    And he forwards for approver TL_SI
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_SI logs in
    And he chooses to act upon above application number
    And he changes trade area as "1200"
    And he forwards for approver TL_Commissioner
    And he confirms to proceed
    And he closes acknowledgement page
    And current user logs out

    When TL_ADM_Commissioner logs in
    And he chooses to act upon above application number
    And he approves application
    And he confirms to proceed
    And he closes acknowledgement page
    And he chooses to act upon above application number
    And he cancel the application
    And he closes acknowledgement page
    And current user logs out

    When TL_PHS_JA logs in
    And he chooses to act upon above application number
    And he rejects the application
    And he confirms to proceed
    And he closes acknowledgement page
    And user will select the required screen as "Search Trade License"
    And he choose to search with license number
    And he verifies the application status
    And user will be notified by "Cancelled"
    And he verifies the License active
    And user will be notified by "YES"
    And he closes search screen
    And current user logs out

    Examples:
      | tradeDetailsData         | tradeLocationData           | tradeDetailsData1        |
      | ownerDetailsTradeLicense | locationDetailsTradeLicense | tradeDetailsTradeLicense |