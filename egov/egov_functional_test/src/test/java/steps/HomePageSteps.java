package steps;

import cucumber.api.java8.En;
import dataBaseFiles.DatabaseReader;
import entities.LoginDetails;
import excelDataFiles.ExcelReader;
import pages.BasePage;
import pages.HomePage;

import java.sql.SQLException;

public class HomePageSteps extends BaseSteps implements En {
    public HomePageSteps() {
        Given("^(.*) logs in$", (String currentUser) -> {
//            LoginDetails loginDetails = new ExcelReader(loginTestDataFileName).getLoginDetails(currentUser);
            LoginDetails loginDetails = null;
            try {
                loginDetails = new DatabaseReader().getLoginDetails(currentUser);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            if (System.getProperty("env").equalsIgnoreCase("qa"))
//                loginDetails.setPassword("********");
            if (currentUser.equals("employee")) {
                pageStore.get(HomePage.class).loginAs(loginDetails, scenarioContext.getUser());
            }
            else{
                pageStore.get(HomePage.class).loginAs(loginDetails);
            }
            scenarioContext.setUser(currentUser);
        });

        And("^the next user will be logged in$", () -> {
            LoginDetails loginDetails = new ExcelReader(loginTestDataFileName).getLoginDetails(scenarioContext.getUser());
//            if (System.getProperty("env").equalsIgnoreCase("qa"))
//                loginDetails.setPassword("********");
            pageStore.get(HomePage.class).loginAs(loginDetails);
        });

        And("^user will be notified by \"([^\"]*)\"$", (String expectedMessage) -> {
            String actualMessage = scenarioContext.getActualMessage();
            pageStore.get(BasePage.class).isSuccesful(expectedMessage, actualMessage);
        });

        Given("^user log on to the website$", () -> {
            pageStore.get(HomePage.class).visitWebsite();
        });
        And("^citizen sign out$", () -> {
            pageStore.get(HomePage.class).signOut();
        });
    }
}
