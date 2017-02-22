package pages.tradeLicense;

import entities.ptis.ApprovalDetails;
import entities.tradeLicense.*;
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

/**
 * Created by bimal on 20/12/16.
 */
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

    @FindBy(id = "buildingType")
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

    @FindBy(className = "select2-search__field")
    private WebElement searchBox;

    @FindBy(id = "close")
    private WebElement closeButton;

    @FindBy(id = "applicationNumber")
    private WebElement applicationNumberTextBox;

    @FindBy(id = "searchtree")
    private WebElement searchTreeBox;

    @FindBy(id = "btnsearch")
    private WebElement searchButton;

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

    @FindBy(id = "category")
    private WebElement tradeCategorySelect;

    @FindBy(id = "approverComments")
    private WebElement approverRemarkTextBox;

    @FindBy(id = "Approve")
    private WebElement approveButton;

    @FindBy(id = "approverComments")
    private WebElement approverCommentsTextBox;

    @FindBy(id = "approverDepartment")
    private WebElement approverDepartmentSelection;

    @FindBy(id = "approverDesignation")
    private WebElement approverDesignationSelection;

    @FindBy(id = "approverPositionId")
    private WebElement approverSelection;

    @FindBy(id = "Forward")
    private WebElement forwardButton;

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

    @FindBy(xpath = ".//*[@id='viewTradeLicense']/div/input")
    private WebElement closeButton1;

    @FindBy(id = "parentBoundary")
    private WebElement wardSelect;

    @FindBy(id = "boundary")
    private WebElement location;


    public TradeLicensePage(WebDriver webDriver) {this.webDriver = webDriver;}

    public void entertradeOwnerDetails(TradeOwnerDetails tradeOwnerDetails) {
        enterText(aadhaarNumberTextBox, tradeOwnerDetails.getAadhaarNumber(),webDriver);
        enterText(mobileNumberTextBox, tradeOwnerDetails.getMobileNumber(), webDriver);
        enterText(tradeOwnerNameTextBox, tradeOwnerDetails.getTradeOwnerName(),webDriver);
        enterText(fatherSpouseNameTextBox,tradeOwnerDetails.getFatherSpouseName(),webDriver);
        enterText(emailIDTextBox, tradeOwnerDetails.getEmailId(),webDriver);
        enterText(tradeOwnerAddressTextBox, tradeOwnerDetails.getTradeOwnerAddress(),webDriver);
    }

    public void entertradeLocationDetails(TradeLocationDetails tradelocationDetails) {
        enterText(propertyAssessmentNumberTextBox, tradelocationDetails.getpropertyAssessmentNumber(),webDriver);
        selectFromDropDown(location,tradelocationDetails.getLocality(), webDriver);
        location.sendKeys(Keys.TAB);
        selectFromDropDown(wardSelect,tradelocationDetails.getWard(), webDriver);
        selectFromDropDown(OwnershipTypeDropBox,tradelocationDetails.getownershipType(),webDriver);
    }

    public void entertradeDetails(TradeDetails tradedetails) {
        enterText(tradeTitleTextBox, tradedetails.getTradeTitle(),webDriver);
        selectFromDropDown(TradeTypeDropBox, tradedetails.gettradeType(), webDriver);
        selectFromDropDown(TradeCategoryDropBox,tradedetails.getTradeCategory(),webDriver);
        try {
            clickOnButton(tradeSubCategoryDropBox,webDriver);
        } catch (StaleElementReferenceException e) {
            WebElement element = webDriver.findElement(By.id("select2-subCategory-container"));
            clickOnButton(element, webDriver);
        }
        searchBox.sendKeys(tradedetails.gettradeSubCategory());
        WebElement element = webDriver.findElement(By.cssSelector(".select2-results__option.select2-results__option--highlighted"));
        clickOnButton(element, webDriver);
        enterText(TradeAreaWeightOfPremises, tradedetails.gettradeAreaWeightOfPremises(),webDriver);
        enterText(remarksTextBox, tradedetails.getremarks(),webDriver);
        enterText(tradeCommencementDateTextBox, tradedetails.gettradeCommencementDate(),webDriver);
        clickOnButton(saveButton,webDriver);
    }


    public String getApplicationNumber() {
        List<WebElement> elements=webDriver.findElements(By.cssSelector(".col-sm-3.col-xs-6.add-margin.view-content"));
        String appNum = elements.get(0).getText();
        clickOnButton(closeButton, webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
        return appNum;
        }

    public void enterApplicationNumber(String applicationNumber) {
        enterText(applicationNumberTextBox , applicationNumber, webDriver);
        jsClickCheckbox(includeInactiveElementCheck, webDriver);
        clickOnButton(searchButton, webDriver);
    }

    public void chooseCollectFees() {
      selectFromDropDown(collectFeeDropBox,"Collect Fees",webDriver);
        switchToNewlyOpenedWindow(webDriver);
    }


    public void chooseToPayTaxOfApplicationNumber() {
        switchToNewlyOpenedWindow(webDriver);
        enterText(amountTextBox , totalAmountReceived.getAttribute("value").split("\\.")[0], webDriver);
        WebElement element = webDriver.findElement(By.id("button2"));
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", element);
        clickOnButton(printClose, webDriver);
        switchToNewlyOpenedWindow(webDriver);
        clickOnButton(closeSearch, webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
        webDriver.navigate().refresh();

    }

    public void chooseOldTradeLicense() {
        enterText(oldTradeLicense,get6DigitRandomInt(),webDriver);
    }

    public void enterlegencyDetails() {
        List<WebElement> elements=webDriver.findElements(By.cssSelector(".form-control.patternvalidation.feeamount"));

            enterText(elements.get(5),"200",webDriver);
            webDriver.switchTo().activeElement();
            jsClick(webDriver.findElement(By.cssSelector(".btn.btn-primary")), webDriver);
    }

    public void enterDetailsForClosure(LicenseClosureDetails closureDetails) {
        selectFromDropDown(statusSelect,closureDetails.getStatusDetails(),webDriver);
        selectFromDropDown(tradeCategorySelect,closureDetails.getTradeCategory(),webDriver);
        jsClick(searchButton,webDriver);
        selectFromDropDown(collectFeeDropBox,"Closure",webDriver);
        switchToNewlyOpenedWindow(webDriver);

    }

    public String getLicenseNumber() {
        List<WebElement> elements= webDriver.findElements(By.cssSelector(".col-xs-3.add-margin.view-content"));
        return elements.get(11).getText();
    }

    public void closeAcknowledgement() {
        webDriver.close();
        switchToNewlyOpenedWindow(webDriver);
        webDriver.close();
        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void applicationApproval() {


        WebElement element = webDriver.findElement(By.id("boundary"));
        for (int i = 0 ; i<=2 ; i++) {
            if (element.getText().equals(null)) {
                WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
                webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("boundary")));
            } else {
                enterText(approverRemarkTextBox, "Approved",webDriver);
                jsClick(approveButton,webDriver);
                break;
            }
        }
    }


    public void forward() {
        jsClick(forwardButton,webDriver);
    }

    public String generateLicenseCertificate() {

        WebElement element = webDriver.findElement(By.id("boundary"));
        for (int i = 0 ; i<=2 ; i++) {
            if (element.getText().equals(null)) {
                WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
                webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("boundary")));
            } else {
                jsClick(generateCertificateButton,webDriver);
                switchToNewlyOpenedWindow(webDriver);
                break;
            }
        }
        WebElement ele=webDriver.findElement(By.xpath("html/body/div[1]/header/nav/div/div[1]/a/div/span"));
        String actMsg=ele.getText();
        webDriver.close();
        switchToPreviouslyOpenedWindow(webDriver);
        return actMsg;

    }

    public void enterApplicationNumberReadingFromExcel(SearchTradeDetails searchId) {
        enterText(applicationNumberTextBox, searchId.getApplicationNumber(),webDriver);
        jsClick(searchButton, webDriver);
    }

    public String getLegacyLicenseNumber() {
        String licenseNum= webDriver.findElement(By.xpath(".//*[@id='viewTradeLicense']/div[8]/div[1]/div[2]")).getText();
        jsClick(closeLicensePage,webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
        return licenseNum;
    }

    public void enterLicenseNumber(String licenseNumber) {
        enterText(licenseNumberBox, licenseNumber,webDriver);
        jsClick(searchButton,webDriver);
    }

    public void chooseToRenewLicense() {
        selectFromDropDown(collectFeeDropBox,"Renew License",webDriver);
        switchToNewlyOpenedWindow(webDriver);
        jsClick(saveButton,webDriver);
        jsClick(closeButton, webDriver);
        switchToNewlyOpenedWindow(webDriver);
        jsClick(searchButton,webDriver);
    }

    public void checkNoOfRecords() {
        int numOfRecords= webDriver.findElements(By.className("dropchange")).size();
        if(numOfRecords>0)
        {
            System.out.println("--------Number of records = "+numOfRecords);
        }
        else
        {
            System.out.println("--------No records found");
        }
        jsClick(closeSearch,webDriver);
        switchToPreviouslyOpenedWindow(webDriver);
    }

    public void enterStatus(String status) {
        selectFromDropDown(statusSelect,status,webDriver);
        jsClick(searchButton, webDriver);
        WebElement show= webDriver.findElement(By.xpath(".//*[@id='tblSearchTrade_length']/label/select"));
        selectFromDropDown(show,"100",webDriver);
    }

    public void closureApproval() {
        enterText(approverRemarkTextBox, "Approved",webDriver);
        jsClick(approveButton,webDriver);
    }

    public void closeAcknowledgementPage() {
        webDriver.close();
        switchToPreviouslyOpenedWindow(webDriver);
    }
}

