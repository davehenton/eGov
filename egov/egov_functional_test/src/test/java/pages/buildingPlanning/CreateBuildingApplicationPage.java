package pages.buildingPlanning;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CreateBuildingApplicationPage extends BasePage {

    // Application Details
    @FindBy(id = "serviceType")
    private WebElement applicationTypeSelectBox;

    @FindBy(id = "applicationAmenity")
    private WebElement multipleAmentiesSelectBox;

    @FindBy(id = "stakeHolderType")
    private WebElement stakeHolderTypeSelectBox;

    @FindBy(id = "stakeHolder")
    private WebElement stakeHolderNameSelectBox;

    @FindBy(id = "occupancy")
    private WebElement occupancySelectBox;

    @FindBy(id = "applicantMode")
    private WebElement applicantModeSelectBox;

    // Applicant Details
    @FindBy(id = "owner.applicantName")
    private WebElement applicantNameTextBox;

    @FindBy(id = "address")
    private WebElement applicantAddressTextArea;

    @FindBy(id = "mobileNumber")
    private WebElement applicantMobileNumberTextBox;

    @FindBy(id = "emailId")
    private WebElement applicantEmailIdTextBox;

    // Site Details
    @FindBy(css = "[id=zone][name=zoneId]")
    private WebElement zoneSelectBox;

    @FindBy(id = "ward")
    private WebElement wardSelectBox;

    @FindBy(name = "siteDetail[0].electionBoundary")
    private WebElement electionWardSelectBox;

    @FindBy(id = "plotnumber")
    private WebElement plotNumberTextBox;

    @FindBy(id = "plotsurveynumber")
    private WebElement plotSurveyNumberTextBox;

    @FindBy(id = "village")
    private WebElement revenueVillageSelectBox;

    @FindBy(id = "state")
    private WebElement stateTextBox;

    @FindBy(name = "siteDetail[0].district")
    private WebElement districtTextBox;

    @FindBy(name = "siteDetail[0].taluk")
    private WebElement talukTextBox;

    @FindBy(id = "natureofOwnership")
    private WebElement natureOfOwnershipTextBox;

    @FindBy(id = "registrarOffice")
    private WebElement registrarOfficeTextBox;

    @FindBy(id = "nearestbuildingnumber")
    private WebElement nearestBuildingNumberTextBox;

    @FindBy(id = "subdivisionNumber")
    private WebElement subDivisionNumberTextBox;

    // Services and Amenities Measurement Details
    @FindBy(id = "extentOfLand")
    private WebElement extentOfLandTextBox;

    @FindBy(id = "roofConversion")
    private WebElement roofConversionTextBox;

    @FindBy(name = "siteDetail[0].noOfPoles")
    private WebElement noOfPolesTextBox;

    @FindBy(id = "noOfHutOrSheds")
    private WebElement noOfHutOrShedsTextBox;

    @FindBy(id = "shutter")
    private WebElement noOfShutterOrDoorsTextBox;

    @FindBy(id = "erectionoftower")
    private WebElement noOfTowersTextBox;

    @FindBy(name = "siteDetail[0].dwellingunitnt")
    private WebElement noOfWellsTextBox;

    @FindBy(id = "siteDetail[0].lengthOfCompoundWall")
    private WebElement lengthOfCompondWallTextBox;

    // Building Details
    @FindBy(name = "buildingDetail[0].totalPlintArea")
    private WebElement totalPlintAreaTextBox;

    @FindBy(id = "buildingheightGround")
    private WebElement buildingheightGroundTextBox;

    @FindBy(id = "floorCount")
    private WebElement floorCountTextBox;

    @FindBy(id = "fromGroundWithWOStairRoom")
    private WebElement fromGroundWithAndWithoutStairRoomTextBox;

    @FindBy(id = "machineRoom")
    private WebElement machineRoomTextbox;

    @FindBy(id = "fromStreetWithWOStairRoom")
    private WebElement fromStreetWithAndWithoutStairRoomTextBox;

    @FindBy(id = "buttonSubmit")
    private WebElement submitButton;

    @FindBy(css = "[name='instrHeaderCash.instrumentAmount']")
    private WebElement amountTextBox;

    @FindBy(id = "button2")
    private WebElement payButton;

    @FindBy(id = "buttonClose")
    private WebElement closeButton;

    // Appointment Details
    @FindBy(linkText = "New Appointment")
    private WebElement appointmentButton;

    @FindBy(id = "appointmentDate")
    private WebElement appointmentDate;

    @FindBy(css = "input[id='appointmentTime']")
    private WebElement appointmentTimeTextBox;

    @FindBy(css = ".glyphicon.glyphicon-time")
    private WebElement presentTimeSelectionButton;

    @FindBy(id = "scheduleSubmit")
    private WebElement scheduleSubmitButton;

    @FindBy(linkText = "Document Scrutiny")
    private List<WebElement> documentScrutinyButton;

    @FindBy(linkText = "Capture Inspection Details")
    private WebElement captureInspectionDetailsButton;

    @FindBy(linkText = "Calculate Fee")
    private WebElement calculateFeeButton;

    @FindBy(id = "createFeeSubmit")
    private WebElement saveFeesButton;

    @FindBy(id = "Approve")
    private WebElement approveButton;

    @FindBy(id = "applicationNumber")
    private WebElement applicationNumberTextBox;

    @FindBy(id = "btnSearch")
    private WebElement bpaApplicationSearchButton;

    @FindBy(className = "dropchange")
    private WebElement applicationActionSelectBox;

    @FindBy(id = "Sign")
    private WebElement digitalSignatureButton;

    @FindBy(id = "Generate Permit Order")
    private WebElement generatePermitOrderButton;

    private WebDriver webDriver;

    public CreateBuildingApplicationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void enterApplicationDetails() {
        selectFromDropDown(applicationTypeSelectBox, "New Construction", webDriver);
        Select amenties = new Select(multipleAmentiesSelectBox);
        multipleAmentiesSelectBox.sendKeys(Keys.CONTROL);
        for (int i = 0; i < amenties.getOptions().size(); i++) {
            amenties.selectByIndex(i);
        }

        selectFromDropDown(stakeHolderTypeSelectBox, "ARCHITECT", webDriver);
        selectFromDropDown(stakeHolderNameSelectBox, "Vasim", webDriver);
        selectFromDropDown(occupancySelectBox, "Residential", webDriver);
//        selectFromDropDown(applicantModeSelectBox, "NEW", webDriver);
    }

    public void enterApplicantDetails() {
        enterText(applicantNameTextBox, "Tester-" + getRandomUpperCaseCharacters(5), webDriver);
        enterText(applicantAddressTextArea, "Address", webDriver);
        enterText(applicantMobileNumberTextBox, "9160977087", webDriver);
        enterText(applicantEmailIdTextBox, "vvsvinaykumar123@gmail.com", webDriver);
    }

    public void enterSiteDetails() {
//        selectFromDropDown(zoneSelectBox, "Zone-1", webDriver);
        selectFromDropDown(wardSelectBox, "Gandhi Road", webDriver);
        selectFromDropDown(electionWardSelectBox, "Election Ward No 1", webDriver);
        enterText(plotNumberTextBox, get6DigitRandomInt().substring(0, 3), webDriver);
        enterText(plotSurveyNumberTextBox, get6DigitRandomInt().substring(0, 4), webDriver);
        selectFromDropDown(revenueVillageSelectBox, "Coachin", webDriver);
        enterText(stateTextBox, "Kerala", webDriver);
        enterText(districtTextBox, "Khozikode", webDriver);
        enterText(talukTextBox, "Khozikode", webDriver);
        enterText(natureOfOwnershipTextBox, "Tester", webDriver);
        enterText(registrarOfficeTextBox, "Tester", webDriver);
        enterText(nearestBuildingNumberTextBox, get6DigitRandomInt().substring(0, 4), webDriver);
        enterText(subDivisionNumberTextBox, get6DigitRandomInt().substring(0, 3), webDriver);
    }

    public void enterServicesAndAmenitiesMeasurementDetails() {
        enterText(extentOfLandTextBox, get6DigitRandomInt().substring(0, 5), webDriver);
        enterText(roofConversionTextBox, get6DigitRandomInt().substring(0, 2), webDriver);
        enterText(noOfPolesTextBox, get6DigitRandomInt().substring(0, 1), webDriver);
        enterText(noOfHutOrShedsTextBox, get6DigitRandomInt().substring(0, 1), webDriver);
        enterText(noOfShutterOrDoorsTextBox, get6DigitRandomInt().substring(0, 1), webDriver);
        enterText(noOfShutterOrDoorsTextBox, get6DigitRandomInt().substring(0, 2), webDriver);
        enterText(noOfTowersTextBox, get6DigitRandomInt().substring(0, 1), webDriver);
        enterText(noOfWellsTextBox, get6DigitRandomInt().substring(0, 1), webDriver);
        enterText(lengthOfCompondWallTextBox, get6DigitRandomInt().substring(0, 3), webDriver);
    }

    public void enterBuildingDetails() {
        enterText(totalPlintAreaTextBox, get6DigitRandomInt().substring(0, 3), webDriver);
        enterText(buildingheightGroundTextBox, get6DigitRandomInt().substring(0, 2), webDriver);
        enterText(floorCountTextBox, get6DigitRandomInt().substring(0, 1), webDriver);
        enterText(fromGroundWithAndWithoutStairRoomTextBox, "7", webDriver);
        enterText(machineRoomTextbox, "8", webDriver);
        enterText(fromStreetWithAndWithoutStairRoomTextBox, "10", webDriver);
    }

    public void submitApplication() {
        clickOnButton(submitButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);
    }

    public String collectFeeFromApplicant() {
        waitForElementToBeVisible(webDriver.findElements(By.cssSelector("[class='greyboxwithlink'] table tbody tr")).get(1).findElements(By.tagName("td")).get(2), webDriver);
        String applicationNumber = webDriver.findElements(By.cssSelector("[class='greyboxwithlink'] table tbody tr")).get(1).findElements(By.tagName("td")).get(2).getText();
        System.out.println("===========" + applicationNumber);
        waitForElementToBeVisible(webDriver.findElement(By.id("totalamounttobepaid")), webDriver);
        String amountTobePaid = webDriver.findElement(By.id("totalamounttobepaid")).getAttribute("value");

        enterText(amountTextBox, amountTobePaid.split("\\.")[0], webDriver);
        clickOnButton(payButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);
        return applicationNumber;
    }

    public void closeAcknowledgementForm() {
        clickOnButton(closeButton, webDriver);
        if (webDriver.getWindowHandles().size() > 1) {
            for (String winHandle : webDriver.getWindowHandles()) {
                String title = webDriver.switchTo().window(winHandle).getCurrentUrl();
                if (title.equals("http://kozhikode-demo.egovernments.org/bpa/application/search")) {
                    break;
                }
            }
            clickOnButton(webDriver.findElement(By.cssSelector(".btn.btn-default")), webDriver);
        }
        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void closeForwardAcknowledgementForm() {
        clickOnButton(webDriver.findElement(By.id("button2")), webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void provideAppointment() {
        clickOnButton(appointmentButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);

        enterDate(appointmentDate, getCurrentDate(), webDriver);
        waitForElementToBeVisible(appointmentTimeTextBox, webDriver);
        clickOnButton(presentTimeSelectionButton, webDriver);
        clickOnButton(scheduleSubmitButton, webDriver);

        switchToNewlyOpenedWindow(webDriver);
        clickOnButton(webDriver.findElement(By.cssSelector(".btn.btn-default")), webDriver);

        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void performDocumentScrutiny() {
        clickOnButton(documentScrutinyButton.get(1), webDriver);
        switchToNewlyOpenedWindow(webDriver);
    }

    public void captureFieldInspectionDetails() {
        clickOnButton(captureInspectionDetailsButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);

        clickOnButton(submitButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);
        clickOnButton(webDriver.findElement(By.id("button2")), webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void calculateFees() {
        clickOnButton(calculateFeeButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);

        clickOnButton(saveFeesButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);
        clickOnButton(webDriver.findElement(By.cssSelector(".btn.btn-default")), webDriver);
        switchToPreviouslyOpenedWindow(webDriver);

        webDriver.navigate().refresh();
    }

    public void approve() {
        clickOnButton(approveButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);
    }

    public void searchApplication(String applicationNumber) {
        enterText(applicationNumberTextBox, applicationNumber, webDriver);
        clickOnButton(bpaApplicationSearchButton, webDriver);
        selectFromDropDown(applicationActionSelectBox, "Collect Fees", webDriver);
        switchToNewlyOpenedWindow(webDriver);
    }

    public void digitalSignature() {
        clickOnButton(digitalSignatureButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);
    }

    public void generatePermitOrder() {
        clickOnButton(generatePermitOrderButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        webDriver.close();
        switchToPreviouslyOpenedWindow(webDriver);
    }
}