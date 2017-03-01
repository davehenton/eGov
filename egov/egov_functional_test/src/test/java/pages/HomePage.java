package pages;

import entities.LoginDetails;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class HomePage extends BasePage {
    private WebDriver driver;

    @FindBy(id = "j_username")
    private WebElement userNameTextBox;

    @FindBy(id = "j_password")
    private WebElement passwordTextBox;

    @FindBy(id = "signin-action")
    private WebElement signInButton;

    @FindBy(id = "locationId")
    private WebElement locationSelection;

    @FindBy(css = ".form-control.style-form.valid")
    private WebElement zoneSelect;

    @FindBy(xpath = "html/body/div[1]/header/nav/div/div[1]/a/img")
    private WebElement logo;

    @FindBy(css = ".dropdown-toggle")
    private WebElement profileLink;

    @FindBy(linkText = "Sign out")
    private WebElement signOutLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void loginAs(LoginDetails loginDetails) {

        enterText(userNameTextBox, loginDetails.getLoginId(), driver);
        enterText(passwordTextBox, loginDetails.getPassword(), driver);
        if (loginDetails.getHasZone()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        WebElement signForm = driver.findElement(By.id("signform"));
        waitForElementToBeClickable(signForm, driver);
        signForm.submit();
    }

    public void visitWebsite() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void signOut() {
        clickOnButton(profileLink, driver);
        clickOnButton(profileLink, driver);
        clickOnButton(signOutLink, driver);
    }
}
