package steps.employeeManagement;

import cucumber.api.java8.En;
import entities.employeeManagement.createEmployee.AssignmentDetails;
import entities.employeeManagement.createEmployee.EmployeeDetails;
import entities.employeeManagement.createEmployee.JurisdictionDetails;
import excelDataFiles.EmployeeManagementDetailsDataReader;
import pages.employeeManagement.employeeCreation.AssignmentDetailsPage;
import pages.employeeManagement.employeeCreation.EmployeeDetailsPage;
import pages.employeeManagement.employeeCreation.EmployeeOtherDetailsPage;
import steps.BaseSteps;

public class EmployeeOperationSteps extends BaseSteps implements En {

    public EmployeeOperationSteps() {

        And("^user enters the employee details as (\\w+) and is read from (\\w+)$", (String employeeDetailsDataId, String readFrom) -> {
            EmployeeDetails employeeDetails = new EmployeeManagementDetailsDataReader(eisTestDataFileName).getEmployeeDetails(employeeDetailsDataId);
            scenarioContext.setApplicationNumber(pageStore.get(EmployeeDetailsPage.class).enterEmployeeDetails(employeeDetails,readFrom));
//            System.out.println("===EMP==="+scenarioContext.getApplicationNumber());
        });

        And("^user will enter the assignment details as (\\w+) and is read from (\\w+)$", (String dataId, String readFrom) -> {
            AssignmentDetails assignmentDetails = new EmployeeManagementDetailsDataReader(eisTestDataFileName).getAssignmentDetails(dataId);
            pageStore.get(AssignmentDetailsPage.class).enterAssignmentDetails(assignmentDetails, readFrom);
        });

        And("^user will enter the jurisdiction details as (\\w+)$", (String dataId) -> {
            JurisdictionDetails jurisdictionDetails = new EmployeeManagementDetailsDataReader(eisTestDataFileName).getJurisdictionDetails(dataId);
            pageStore.get(EmployeeOtherDetailsPage.class).enterJurisdictionDetails(jurisdictionDetails);
        });
        Then("^user clicks on submit button$", () -> {
            pageStore.get(EmployeeOtherDetailsPage.class).submitCreateEmployee();

        });

        Then("^user close the employee search$", () -> {
            pageStore.get(EmployeeOtherDetailsPage.class).closeEmployeeSearch();
        });

        And("^user will enter the service section and other details$", () -> {
            pageStore.get(EmployeeOtherDetailsPage.class).enterServiceSectionDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).enterProbationDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).enterRegularisationDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).enterEducationDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).enterTechnicalQualificationDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).enterDepartmentalTestDetails();
        });

        And("^user will enter the employee search details$", () -> {
            pageStore.get(EmployeeOtherDetailsPage.class).searchEmployeeFormViewScreen(scenarioContext.getApplicationNumber());
        });

        And("^user will enter the employee search details for updating the employee information$", () -> {
//            pageStore.get(EmployeeOtherDetailsPage.class).searchEmployeeFromUpdateScreen(scenarioContext.getApplicationNumber());
            pageStore.get(EmployeeOtherDetailsPage.class).searchEmployeeFromUpdateScreen(scenarioContext.getApplicationNumber());
        });

        And("^user will update the employee details$", () -> {
            pageStore.get(EmployeeDetailsPage.class).updateEmployeeDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).updateServiceSectionDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).updateProbationDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).updateRegularisationDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).updateEducationDetails();
            pageStore.get(EmployeeOtherDetailsPage.class).updateTechnicalQualificationDetails();
        });
        And("^user test the fields validation for employee details$", () -> {
            scenarioContext.setApplicationNumber(pageStore.get(EmployeeDetailsPage.class).checkEmployeeTabFields());
        });
        And("^user test the fields validation for assisgnment details$", () -> {
           pageStore.get(AssignmentDetailsPage.class).checkValidationFieldsInAssisgnmentTab();
        });
        And("^user test the fields validation for service and other details$", () -> {
          pageStore.get(EmployeeOtherDetailsPage.class).checkValidationForServiceDetails();
          pageStore.get(EmployeeOtherDetailsPage.class).enterProbationDetails();
          pageStore.get(EmployeeOtherDetailsPage.class).enterRegularisationDetails();
          pageStore.get(EmployeeOtherDetailsPage.class).checkEducationDetailsFields();
          pageStore.get(EmployeeOtherDetailsPage.class).enterTechnicalQualificationDetails();
          pageStore.get(EmployeeOtherDetailsPage.class).enterDepartmentalTestDetails();
        });
        And("^user enters the employee details as (\\w+)$", (String employeeDetailsDataId) -> {
            EmployeeDetails employeeDetails = new EmployeeManagementDetailsDataReader(productionDumpDataFileName).getEmployeeDetails(employeeDetailsDataId);
            pageStore.get(EmployeeDetailsPage.class).createEmployeeForProductionDump(employeeDetails);
        });
        And("^user will enter the assignment details as (\\w+)$", (String assignmentDetailsDataId) -> {
            AssignmentDetails assignmentDetails = new EmployeeManagementDetailsDataReader(productionDumpDataFileName).getAssignmentDetails(assignmentDetailsDataId);
            pageStore.get(AssignmentDetailsPage.class).enterAssignmentDetailsForProductionDump(assignmentDetails);
        });
        And("^user selects user name for searching role as (\\w+)$", (String userRoleID) -> {
            EmployeeDetails userRoleDetails = new EmployeeManagementDetailsDataReader(productionDumpDataFileName).getEmployeeDetails(userRoleID);
            pageStore.get(EmployeeOtherDetailsPage.class).userRoleDetailsForProductionDump(userRoleDetails);
        });
        And("^user updates particular roles for an employee as (\\w+)$", (String rolesId) -> {
            AssignmentDetails userRoles = new EmployeeManagementDetailsDataReader((productionDumpDataFileName)).getAssignmentDetails(rolesId);
            pageStore.get(AssignmentDetailsPage.class).userRoleDetailsForProductionDump(userRoles);
        });
    }
}
