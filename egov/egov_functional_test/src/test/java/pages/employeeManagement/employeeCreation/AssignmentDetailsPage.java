package pages.employeeManagement.employeeCreation;

import entities.employeeManagement.createEmployee.AssignmentDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

public class AssignmentDetailsPage extends BasePage {

    @FindBy(css = ".glyphicon.glyphicon-plus")
    private WebElement addImageButton;

    @FindBy(css = "input[id='assignments.isPrimary'][value='true']")
    private WebElement isPrimaryTrueRadio;

    @FindBy(css = "input[id='assignments.isPrimary'][value='false']")
    private WebElement isPrimaryFalseRadio;

    @FindBy(css = "input[id='assignments.fromDate']")
    private WebElement fromDateTextBox;

    @FindBy(css = "input[id='assignments.toDate']")
    private WebElement toDateTextBox;

    @FindBy(css = "select[id='assignments.department']")
    private WebElement departmentSelectBox;

    @FindBy(css = "select[id='assignments.designation']")
    private WebElement designationSelectBox;

    @FindBy(css = "[id='assignments.position']")
    private WebElement positionSelectBox;

    @FindBy(css = "select[id='assignments.grade']")
    private WebElement gradeSelectBox;

    @FindBy(css = "select[id='assignments.function']")
    private WebElement functionSelectBox;

    @FindBy(css = "select[id='assignments.functionary']")
    private WebElement functionarySelectBox;

    @FindBy(css = "select[id='assignments.fund']")
    private WebElement fundSelectBox;

    @FindBy(css = "input[id='assignments.hod'][value='true']")
    private WebElement isHODTrueRadioButton;

    @FindBy(css = "input[id='assignments.hod'][value='false']")
    private WebElement isHODFalseRadioButton;

    @FindBy(css = "input[id='assignments.govtOrderNumber']")
    private WebElement govtOrderNumberTextBox;

    @FindBy(css = "input[id='assignments.documents']")
    private WebElement attachFileButton;

    @FindBy(css = ".btn.btn-primary")
    private WebElement addOrEditButton;

    @FindBy(id = "addEmployee")
    private WebElement submitButton;

    @FindBy(id = "assignments.govtOrderNumber-error")
    private WebElement govtOrderNumbererror;

    @FindBy(id = "userroleUpdateBtn")
    private WebElement userRoleUpdateButton;

    @FindBy(css = "select[id='multiselect']")
    private WebElement selectRoles;

    @FindBy(id = "multiselect_rightSelected")
    private WebElement multiselectRightSelected;

    @FindBy(id = "multiselect_leftSelected")
    private WebElement multiselectLeftSelected;

    @FindBy(css = ".btn.btn-default")
    private WebElement roleCloseButton;

    //    @FindBy(xpath = ".//*[@id='agreementTableBody']/td[14]/button[1]")
    @FindBy(css = "td[data-label= 'Action'] button")
    private List<WebElement> editButtons;

    private WebDriver webDriver;

    public AssignmentDetailsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void enterAssignmentDetails(AssignmentDetails assignmentDetails, String readFrom) {
        webDriver.manage().window().maximize();
        jsClick(webDriver.findElement(By.cssSelector("a[href='#assignmentDetails']")), webDriver);
        jsClick(addImageButton, webDriver);
        if (assignmentDetails.getIsPrimary().equalsIgnoreCase("Yes")) {
            clickOnButton(isPrimaryTrueRadio, webDriver);
        } else {
            clickOnButton(isPrimaryFalseRadio, webDriver);
        }
        enterDate(fromDateTextBox, getCurrentDate(), webDriver);
        enterDate(toDateTextBox, getFutureDate(30), webDriver);
        selectFromDropDown(departmentSelectBox, assignmentDetails.getDepartment(), webDriver);
        selectFromDropDown(designationSelectBox, assignmentDetails.getDesignation(), webDriver);

        if (readFrom.equalsIgnoreCase("TRUE")) {
            enterDate(toDateTextBox, getFutureDate(300), webDriver);
            enterText(positionSelectBox, assignmentDetails.getPosition(), webDriver);
        } else
            await().atMost(10, SECONDS).until(() -> webDriver.findElements(By.cssSelector("[class='col-sm-6'] [id='assignments.position']")).size() == 1);
        designationSelectBox.sendKeys(Keys.TAB);
        await().atMost(25, SECONDS).until(() -> webDriver.findElements(By.cssSelector("li[class=ui-menu-item]")).size() >= 1);

        clickOnButton(webDriver.findElements(By.cssSelector("li[class=ui-menu-item]")).get(0).findElement(By.tagName("div")), webDriver);
        clickOnButton(addOrEditButton, webDriver);
    }


    public void checkValidationFieldsInAssisgnmentTab() {
        await().atMost(10, TimeUnit.SECONDS).until(() -> webDriver.findElements(By.cssSelector("[id='agreementTableBody'] tr")).size() > 0);
        jsClick(webDriver.findElements(By.cssSelector("td[data-label='Action'] button")).get(0), webDriver);
        enterText(govtOrderNumberTextBox, "@@@@@", webDriver);
        govtOrderNumberTextBox.sendKeys(Keys.TAB);
        if (govtOrderNumbererror.getText().equals("Only alphanumeric with -/_ allowed.")) {
            enterText(govtOrderNumberTextBox, "1234", webDriver);
        }
        clickOnButton(addOrEditButton, webDriver);
    }

    public void enterAssignmentDetailsForProductionDump(AssignmentDetails assignmentDetails) {
        webDriver.manage().window().maximize();
        jsClick(webDriver.findElement(By.cssSelector("a[href='#assignmentDetails']")), webDriver);
        jsClick(addImageButton, webDriver);
        clickOnButton(isPrimaryTrueRadio, webDriver);
        enterDate(fromDateTextBox, getCurrentDate(), webDriver);
        enterDate(toDateTextBox, getFutureDate(300), webDriver);
        selectFromDropDown(departmentSelectBox, assignmentDetails.getDepartment(), webDriver);
        selectFromDropDown(designationSelectBox, assignmentDetails.getDesignation(), webDriver);
        enterText(positionSelectBox, assignmentDetails.getPosition(), webDriver);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickOnButton(webDriver.findElements(By.cssSelector("li[class=ui-menu-item]")).get(0).findElement(By.tagName("div")), webDriver);
        clickOnButton(addOrEditButton, webDriver);
    }

    public void userRoleDetailsForProductionDump(AssignmentDetails userRoleDetails) {
        clickOnButton(userRoleUpdateButton, webDriver);
        if (userRoleDetails.getRoles().contains(",")) {
            for (int i = 0; i < userRoleDetails.getRoles().split(",").length; i++) {
                selectFromDropDown(selectRoles, userRoleDetails.getRoles().split(",")[i], webDriver);
            }
        } else {
            selectFromDropDown(selectRoles, userRoleDetails.getRoles(), webDriver);
        }
        clickOnButton(multiselectRightSelected, webDriver);
        clickOnButton(userRoleUpdateButton, webDriver);
        clickOnButton(roleCloseButton, webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
    }
}
