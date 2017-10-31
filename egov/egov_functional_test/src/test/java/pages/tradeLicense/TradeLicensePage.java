package pages.tradeLicense;

import entities.tradeLicense.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TradeLicensePage extends BasePage {
    private WebDriver webDriver;

    @FindBy(id = "adhaarId")
    private WebElement aadhaarNumberTextBox;

    @FindBy(id = "mobilePhoneNumber")
    private WebElement mobileNumberTextBox;

    @FindBy(id = "applicantName")
    private WebElement tradeOwnerNameTextBox;

    @FindBy(id = "fatherOrSpouseName")
    private WebElement fatherSpouseNameTextBox;

    @FindBy(id = "emailId")
    private WebElement emailIDTextBox;

    @FindBy(id = "licenseeAddress")
    private WebElement tradeOwnerAddressTextBox;

    @FindBy(id = "propertyNo")
    private WebElement propertyAssessmentNumberTextBox;

    @FindBy(id = "ownershipType")
    private WebElement OwnershipTypeDropBox;

    @FindBy(id = "nameOfEstablishment")
    private WebElement tradeTitleTextBox;

    //    @FindBy(id = "buildingType")
    @FindBy(css = "select[name=natureOfBusiness]")
    private WebElement TradeTypeDropBox;

    @FindBy(id = "category")
    private WebElement TradeCategoryDropBox;

    @FindBy(id = "select2-subCategory-container")
    private WebElement tradeSubCategoryDropBox;

    @FindBy(id = "tradeArea_weight")
    private WebElement TradeAreaWeightOfPremises;

    @FindBy(id = "remarks")
    private WebElement remarksTextBox;

    @FindBy(id = "startDate")
    private WebElement tradeCommencementDateTextBox;

    @FindBy(id = "btnsave")
    private WebElement saveButton;

    @FindBy(id = "Save")
    private WebElement createButton;

    @FindBy(css = "input[class='select2-search__field']")
    private WebElement searchBox;

    @FindBy(id = "close")
    private WebElement closeButton;

    @FindBy(id = "applicationNumber")
    private WebElement applicationNumberTextBox;

    @FindBy(id = "btnsearch")
    private WebElement searchButton;

    @FindBy(css = "button[class='btn btn-default'][type='reset']")
    private WebElement searchResetButton;

    @FindBy(id = "recordActions")
    private WebElement collectFeeDropBox;

    @FindBy(id = "instrHeaderCash.instrumentAmount")
    private WebElement amountTextBox;

    @FindBy(id = "totalamounttobepaid")
    private WebElement totalAmountReceived;

    @FindBy(id = "oldLicenseNumber")
    private WebElement oldTradeLicense;

    @FindBy(id = "buttonClose")
    private WebElement printClose;

    @FindBy(id = "status")
    private WebElement statusSelect;

    @FindBy(id = "approverComments")
    private WebElement approverRemarkTextBox;

    @FindBy(id = "Approve")
    private WebElement approveButton;

    @FindBy(id = "Generate Certificate")
    private WebElement generateCertificateButton;

    @FindBy(xpath = ".//*[@id='button'][@class='button']")
    private WebElement closeLicensePage;

    @FindBy(id = "licenseNumber")
    private WebElement licenseNumberBox;

    @FindBy(xpath = ".//*[@id='searchForm']/div[2]/div/button[3]")
    private WebElement closeSearch;

    @FindBy(id = "inactive")
    private WebElement includeInactiveElementCheck;

    @FindBy(id = "parentBoundary")
    private WebElement wardSelect;

    @FindBy(id = "boundary")
    private WebElement location;

    @FindBy(css = "input[type='button'][value='Close']")
    private WebElement acknowlwdgementClose;

    @FindBy(linkText = "Continue to payment")
    private WebElement continuePayButton;

    @FindBy(id = "generatedemand")
    private WebElement generateDemandButton;

    @FindBy(id = "btncancel")
    private WebElement cancelButton;

    @FindBy(id = "Reject")
    private WebElement rejectButton;

    @FindBy(id = "address")
    private WebElement tradeAddress;

    public TradeLicensePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void entertradeOwnerDetails(TradeOwnerDetails tradeOwnerDetails) {
        enterText(aadhaarNumberTextBox, tradeOwnerDetails.getAadhaarNumber(), webDriver);
        enterText(mobileNumberTextBox, tradeOwnerDetails.getMobileNumber(), webDriver);
        enterText(tradeOwnerNameTextBox, tradeOwnerDetails.getTradeOwnerName(), webDriver);
        enterText(fatherSpouseNameTextBox, tradeOwnerDetails.getFatherSpouseName(), webDriver);
        enterText(emailIDTextBox, tradeOwnerDetails.getEmailId(), webDriver);
        enterText(tradeOwnerAddressTextBox, tradeOwnerDetails.getTradeOwnerAddress(), webDriver);
    }

    public void entertradeLocationDetails(TradeLocationDetails tradelocationDetails) {
//        enterText(propertyAssessmentNumberTextBox, tradelocationDetails.getpropertyAssessmentNumber(), webDriver);
//        propertyAssessmentNumberTextBox.sendKeys(Keys.TAB);
        Select select = new Select(location);
        select.selectByIndex(6);
        location.sendKeys(Keys.TAB);
        selectAParticularFromDropDown(wardSelect, 1, webDriver);
//        new Select(wardSelect).selectByIndex(1);
        await().atMost(20, SECONDS).until(() -> new Select(location).getOptions().size() > 1);
        if (!(wardSelect.findElements(By.tagName("option")).size() > 1)) {
            clickOnButton(location, webDriver);
            clickOnButton(location, webDriver);
            location.sendKeys(Keys.TAB);
        }
        await().atMost(10, SECONDS).until(() -> wardSelect.findElements(By.tagName("option")).size() > 1);
        waitForElementToBeClickable(wardSelect, webDriver);
        new Select(wardSelect).selectByIndex(1);
        selectFromDropDown(OwnershipTypeDropBox, tradelocationDetails.getownershipType(), webDriver);
        enterText(tradeAddress, "Bangalore", webDriver);
    }

    public void entertradeDetails(TradeDetails tradedetails) {
        enterText(tradeTitleTextBox, tradedetails.getTradeTitle(), webDriver);
        selectFromDropDown(TradeTypeDropBox, tradedetails.gettradeType(), webDriver);
        selectFromDropDown(TradeCategoryDropBox, tradedetails.getTradeCategory(), webDriver);
        enterText(TradeAreaWeightOfPremises, tradedetails.gettradeAreaWeightOfPremises(), webDriver);

        try {
            clickOnButton(tradeSubCategoryDropBox, webDriver);
        } catch (Exception e) {
            WebElement element = webDriver.findElement(By.id("select2-subCategory-container"));
            clickOnButton(element, webDriver);
        }

        if (webDriver.findElements(By.id("select2-subCategory-container")).size() == 0) {
            WebElement element = webDriver.findElement(By.id("select2-subCategory-container"));
            clickOnButton(element, webDriver);
        }
        await().atMost(10, TimeUnit.SECONDS).until(() -> webDriver.findElements(By.cssSelector("input[class='select2-search__field']")).size() == 1);
        WebElement search = webDriver.findElement(By.cssSelector("input[class='select2-search__field']"));
        search.sendKeys(tradedetails.gettradeSubCategory());
        await().atMost(10, TimeUnit.SECONDS).until(() -> webDriver.findElements(By.xpath(".//*[@id='select2-subCategory-results']/li[1]")).size() == 1);
        WebElement element = webDriver.findElement(By.xpath(".//*[@id='select2-subCategory-results']/li[1]"));
        clickOnButton(element, webDriver);

        if (tradeSubCategoryDropBox.getText().isEmpty()) {
            WebElement element2 = webDriver.findElement(By.id("select2-subCategory-container"));
            clickOnButton(element2, webDriver);
            waitForElementToBeVisible(searchBox, webDriver);
            searchBox.sendKeys(tradedetails.gettradeSubCategory());
            WebElement element1 = webDriver.findElement(By.xpath(".//*[@id='select2-subCategory-results']/li[1]"));
            clickOnButton(element1, webDriver);
        }


        enterText(remarksTextBox, tradedetails.getremarks(), webDriver);
        enterText(tradeCommencementDateTextBox, tradedetails.gettradeCommencementDate(), webDriver);

    }

    public String getApplicationNumber() {
        List<WebElement> elements = webDriver.findElements(By.cssSelector(".col-sm-3.col-xs-6.add-margin.view-content"));
        String appNum = elements.get(0).getText();
        clickOnButton(closeButton, webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
        return appNum;
    }

    public void enterApplicationNumber(String applicationNumber) {
        enterText(applicationNumberTextBox, applicationNumber, webDriver);
//        jsClickCheckbox(includeInactiveElementCheck, webDriver);
        clickOnButton(searchButton, webDriver);
    }

    public void chooseAction(String action) {
        await().atMost(20, SECONDS).until(() -> collectFeeDropBox.isDisplayed());
        selectFromDropDown(collectFeeDropBox, action, webDriver);
        switchToNewlyOpenedWindow(webDriver);
    }

    public void chooseToPayTaxOfApplicationNumber() {
        int tot = 0;
        for (int i = 1; i <= webDriver.findElements(By.xpath(".//*[@id='LicenseBillCollect']/table/tbody/tr")).size(); i++) {
            String totalAmt = webDriver.findElement(By.xpath(".//*[@id='LicenseBillCollect']/table/tbody/tr[" + i + "]/td[3]")).getText();
            tot = tot + Integer.parseInt(totalAmt);
        }
        clickOnButton(continuePayButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);
        Assert.assertEquals(tot, Integer.parseInt(totalAmountReceived.getAttribute("value").split("\\.")[0]));
        enterText(amountTextBox, totalAmountReceived.getAttribute("value").split("\\.")[0], webDriver);
        WebElement element = webDriver.findElement(By.id("button2"));
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click();", element);
//        clickOnButton(printClose, webDriver);
        await().atMost(20,TimeUnit.SECONDS).until(()->webDriver.findElements(By.className("title2")).size()==1);
        webDriver.close();
//        clickOnButton(webDriver.findElement(By.name("buttonClose")), webDriver);
        for (String winHandle : webDriver.getWindowHandles()) {
            String title = webDriver.switchTo().window(winHandle).getCurrentUrl();
            if (title.equals(getEnvironmentURL() + "/tl/search/license#no-back")) {
                break;
            }
        }
        clickOnButton(closeSearch, webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
        webDriver.navigate().refresh();
    }

    public void chooseOldTradeLicense() {
        enterText(oldTradeLicense, get6DigitRandomInt(), webDriver);
    }

    public void enterlegencyDetails() {
        List<WebElement> elements = webDriver.findElements(By.cssSelector(".form-control.patternvalidation.feeamount"));

        enterText(elements.get(5), "200", webDriver);
//        webDriver.switchTo().activeElement();
//        jsClick(webDriver.findElement(By.cssSelector(".btn.btn-primary")), webDriver);
    }

    public void enterDetailsForClosure(LicenseClosureDetails closureDetails) {
        selectFromDropDown(statusSelect, closureDetails.getStatusDetails(), webDriver);
        selectFromDropDown(TradeCategoryDropBox, closureDetails.getTradeCategory(), webDriver);
        jsClick(searchButton, webDriver);
        selectFromDropDown(collectFeeDropBox, "Closure", webDriver);
        switchToNewlyOpenedWindow(webDriver);
    }

    public String getLicenseNumber() {
        List<WebElement> elements = webDriver.findElements(By.cssSelector(".col-xs-3.add-margin.view-content"));
        return elements.get(11).getText();
    }

    public void closeAcknowledgement() {
        webDriver.close();
        switchToNewlyOpenedWindow(webDriver);
        webDriver.close();
        switchToPreviouslyOpenedWindow(webDriver);
    }
// to be removed

    public void applicationApproval() {


        WebElement element = webDriver.findElement(By.id("boundary"));
        for (int i = 0; i <= 2; i++) {
            if (element.getText().equals(null)) {
                WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
                webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("boundary")));
            } else {
                enterText(approverRemarkTextBox, "Approved", webDriver);
                jsClick(approveButton, webDriver);
                break;
            }
        }
    }

    public void generateLicenseCertificate() {

        WebElement element = webDriver.findElement(By.id("boundary"));
        for (int i = 0; i <= 2; i++) {
            if (element.getText().equals(null)) {
                WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
                webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("boundary")));
            } else {
                jsClick(generateCertificateButton, webDriver);
                webDriver.switchTo().activeElement();
                jsClick(webDriver.findElement(By.cssSelector(".btn.btn-danger")), webDriver);
                switchToNewlyOpenedWindow(webDriver);
                break;
            }
        }

        webDriver.close();
        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void enterApplicationNumberReadingFromExcel(SearchTradeDetails searchId) {
        enterText(applicationNumberTextBox, searchId.getApplicationNumber(), webDriver);
        jsClick(searchButton, webDriver);
    }

    public String getLegacyLicenseNumber() {
        String licenseNum = webDriver.findElement(By.xpath(".//*[@id='tradeLicense']/div[8]/div[1]/div[2]")).getText();
//        jsClick(closeLicensePage, webDriver);
        waitForElementToBeClickable(webDriver.findElement(By.xpath(".//*[@id='buttondiv']/table/tbody/tr/td[2]/button")), webDriver);
//        webDriver.findElement(By.xpath(".//*[@id='buttondiv']/table/tbody/tr/td[2]/button")).click();
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", webDriver.findElement(By.xpath(".//*[@id='buttondiv']/table/tbody/tr/td[2]/button")));
        switchToPreviouslyOpenedWindow(webDriver);
        return licenseNum;
    }

    public void enterLicenseNumber(String licenseNumber) {
        clickOnButton(searchResetButton, webDriver);
        enterText(licenseNumberBox, licenseNumber, webDriver);
        jsClick(searchButton, webDriver);
    }

    public void chooseToRenewLicense() {
        jsClick(saveButton, webDriver);
        jsClick(closeButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);
        jsClick(searchButton, webDriver);
    }

    public void checkNoOfRecords() {
        int numOfRecords = webDriver.findElements(By.className("dropchange")).size();
        if (numOfRecords > 0) {
            System.out.println("--------Number of records = " + numOfRecords);
        } else {
            System.out.println("--------No records found");
        }
        jsClick(closeSearch, webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void enterStatus(String status) {
        selectFromDropDown(statusSelect, status, webDriver);
        jsClick(searchButton, webDriver);
        WebElement show = webDriver.findElement(By.xpath(".//*[@id='tblSearchTrade_length']/label/select"));
        selectFromDropDown(show, "100", webDriver);
    }

    public void closureApproval() {
        enterText(approverRemarkTextBox, "Approved", webDriver);
        jsClick(approveButton, webDriver);
    }

    public void closeAcknowledgementPage() {
        clickOnButton(acknowlwdgementClose, webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void confirmToProceed() {
        webDriver.switchTo().activeElement();
        jsClick(webDriver.findElement(By.cssSelector(".btn.btn-danger")), webDriver);
    }

    public String generateDemand() {
        clickOnButton(generateDemandButton, webDriver);
        String actMsg = webDriver.findElement(By.xpath(".//*[@id='generatelicensedemand']/div/div[1]")).getText();
        webDriver.findElement(By.linkText("Close")).click();
        switchToNewlyOpenedWindow(webDriver);
        clickOnButton(searchButton, webDriver);
        return actMsg;
    }

    public void changeTradeArea(String tradeArea) {
        enterText(TradeAreaWeightOfPremises, tradeArea, webDriver);
    }

    public void cancelApplication() {
        clickOnButton(cancelButton, webDriver);
    }

    public void applicationRejection() {
        enterText(approverRemarkTextBox, "Rejected", webDriver);
        clickOnButton(rejectButton, webDriver);
    }

    public String applicationStatus() {
        String status = webDriver.findElement(By.xpath(".//*[@id='tblSearchTrade']/tbody/tr[1]/td[11]")).getText();
        return status;
    }

    public String licenseStatus() {
        String status = webDriver.findElement(By.xpath(".//*[@id='tblSearchTrade']/tbody/tr[1]/td[12]")).getText();
        return status;
    }

    public void closeSearchScreen() {
        clickOnButton(closeSearch, webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void saveApplication() {
        if (webDriver.findElements(By.name("approverComments")).size() == 1) {
            enterText(webDriver.findElement(By.name("approverComments")), "Comments", webDriver);
            clickOnButton(createButton, webDriver);
        } else
            clickOnButton(saveButton, webDriver);
    }
}

