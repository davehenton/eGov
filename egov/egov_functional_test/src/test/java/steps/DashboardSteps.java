package steps;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pages.DashboardPage;

public class DashboardSteps extends BaseSteps implements En {
    public DashboardSteps() {
        When("^he chooses to create new property$", () -> {
            pageStore.get(DashboardPage.class).chooseToCreateNewProperty();
        });
        And("^current user logs out$", () -> {
            pageStore.get(DashboardPage.class).logOut();
        });
        And("^chooses to act upon the above application$", () -> {
            pageStore.get(DashboardPage.class).openApplication(scenarioContext.getApplicationNumber());
        });
        And("^chooses to act upon the above assessment$", () -> {
            // Write code here that turns the phrase above into concrete actions
            pageStore.get(DashboardPage.class).openApplication(scenarioContext.getAssessmentNumber());
        });
        When("^he chooses to collect taxes$", () -> {
            pageStore.get(DashboardPage.class).chooseToCollectTaxes();
        });
        When("^user chooses to data entry screen$", () -> {
            pageStore.get(DashboardPage.class).chooseToCreateNewDataEntryScreen();
        });
    }
}
