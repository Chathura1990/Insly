package applicationFiles.app_manager.signupPageHelper;

import applicationFiles.app_manager.model_data.SignupPageData;
import applicationFiles.app_manager.selector_helper.SelectorService;
import applicationFiles.framework.mainClass.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

import static applicationFiles.app_manager.ApplicationManager.reportLog;
import static applicationFiles.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SignupPage extends SelectorService {

    public static String GET_COMPANY_NAME;
    public static String COPY_PASSWORD;
    public int COUNTRY_OPTION_ID;

    private By PAGE_HEADER = xpath("//div[@class='container']/div/div/h1");
    //Company block locators
    private By COMPANY_NAME = id("broker_name");
    private By COUNTRY_NAME = id("broker_address_country");
    private By WEB_ADDRESS = id("broker_tag");
    private By ICON_OK = xpath("//*[@id='status_broker_tag']/span");
    private By COMPANY_PROFILE = id("prop_company_profile");
    private By NUMBER_OF_EMPLOYEES = id("prop_company_no_employees");
    private By HOW_WOULD_YOU_DESC = id("prop_company_person_description");
    //Administrator account details block locators
    private By ADMIN_EMAIL = id("broker_admin_email");
    private By ADMIN_NAME = id("broker_admin_name");
    private By PASSWORD_INPUT = id("broker_person_password");
    private By REPEAT_PASSWORD_INPUT = id("broker_person_password_repeat");
    private By SECURE_PASSWORD_LINK = xpath("//input[@id='broker_person_password']/following::a[contains(text(),'suggest a secure password')]");
    private By PASSWORD = xpath("//div[@id='insly_alert']/b");
    private By OK_BUTTON = xpath("//button[contains(text(),'OK')]");
    private By PHONE_NUMBER = id("broker_admin_phone");
    //Terms and conditions block locators
    private By FIRST_CHECKBOX = xpath("//div[@class='checklist']/label[1]/span"); //1st checkbox: "I agree to the terms and conditions"
    private By SECOND_CHECKBOX = xpath("//div[@class='checklist']/label[2]/span"); //2nd checkbox: "I agree to the privacy policy"
    private By PRIVACY_POLICY_LINK = xpath("//div[@class='checklist']/label[2]/a");
    private By THIRD_CHECKBOX = xpath("//div[@class='checklist']/label[3]/span"); //3rd checkbox: "I agree to the processing and storage of my personal data"
    private By DEFINITIONS = xpath("//div[@id='document-content']/h3[1]"); //One of headers inside the privacy policy document
    private By LAST_DIV_OF_THE_DOCUMENT = xpath("//*[@id='document-content']/p[31]");
    private By CLOSE_BUTTON = xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable']/div/a/span");
    //Signup button locator
    private By SIGHN_UP_BUTTON = id("submit_save");
    //Success Page locator
    private By SUCCESS_PAGE_HEADING = xpath("//*[contains(text(),'Sign up and start using')]");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    //Number of execution steps are according to the provided test suite.

    /**
     * 1st Execution steps. Check Website title, check web page Header
     */
    public void getPageTitle(){
        String title = driver.getTitle();
        reportLog("Sites Expected Title--> " + "'Insly' " +"||"+" Site Actual Title--> '"+title+"'");
        Assert.assertEquals(title,"Insly");
    }

    public void checkPageHeader() {
        String headerContent = visibilityOfElementLocatedBylocator(PAGE_HEADER, SECONDS20).getText();
        if(!headerContent.isEmpty()){
            reportLog("Page Header is: '"+headerContent+"'");
            Assert.assertEquals(headerContent,"Sign up and start using");
        }else {
            reportLog("--- PAGE HEADER IS NOT AVAILABLE ---");
        }
    }

    /**
     * 2nd Execution steps. Fill Company block.
     * @param spd details of the company
     */
    public void fillCompanyBlockForm(SignupPageData spd) throws InterruptedException {
        GET_COMPANY_NAME = spd.getCompanyName(); //copy company name to assert the login link
        type(COMPANY_NAME,GET_COMPANY_NAME);
        COUNTRY_OPTION_ID = randomNumb(1,246); //copy random number to get country code. Check 'getPhoneCountryCode()' method
        selectAnOptionFromDropdown(COUNTRY_NAME, COUNTRY_OPTION_ID);
        sleep(MILLISEC2000);
        if(visibilityOfElementLocatedBylocator(WEB_ADDRESS,SECONDS20).isDisplayed()
                && visibilityOfElementLocatedBylocator(ICON_OK,SECONDS20).getAttribute("class").equals("icon-ok")){
            reportLog("Web address is filled automatically");
        }
        assertTrue(selectAnOptionFromDropdown(COMPANY_PROFILE,randomNumb(1,5)));
        assertTrue(selectAnOptionFromDropdown(NUMBER_OF_EMPLOYEES,randomNumb(1,5)));
        assertTrue(selectAnOptionFromDropdown(HOW_WOULD_YOU_DESC,randomNumb(1,4)));
    }

    public String getPhoneCountryCode(){
        Select select = new Select(driver.findElement(COUNTRY_NAME));
        List<WebElement> options = select.getOptions();
        return options.get(COUNTRY_OPTION_ID).getAttribute("data-phonecode");
    }

    /**
     * 3rd Execution steps. Fill Administrator account details block.
     * @param spd details of the Admin
     */
    public void fillAdminAccountDetailsForm(SignupPageData spd){
        type(ADMIN_EMAIL,spd.getAdminEmail());
        type(ADMIN_NAME,spd.getAdminName());
        click(SECURE_PASSWORD_LINK,"[suggest a secure password] link");
        if (visibilityOfElementLocatedBylocator(PASSWORD,SECONDS20).isDisplayed()){
            COPY_PASSWORD = getText(PASSWORD);
            reportLog("Password is: "+ COPY_PASSWORD);
            click(OK_BUTTON,"[Ok] button");
        }
        checkPasswordFields();
        type(PHONE_NUMBER, spd.getAdminPhone());
    }

    private void checkPasswordFields(){
        if (!visibilityOfElementLocatedBylocator(PASSWORD_INPUT,SECONDS20).getText().isEmpty() &&
        !visibilityOfElementLocatedBylocator(REPEAT_PASSWORD_INPUT,SECONDS20).getText().isEmpty()){
            reportLog("Password is filled automatically");
        }
    }

    /**
     * 4th Execution steps. Terms and conditions block.
     */
    public void clickCheckBoxesAndReadPrivacyPolicy() throws InterruptedException {
        click(FIRST_CHECKBOX,"'terms and conditions' checkbox");
        click(SECOND_CHECKBOX,"'privacy policy' checkbox");
        click(THIRD_CHECKBOX,"'processing and storage of personal data' checkbox");
        click(PRIVACY_POLICY_LINK,"'privacy policy' link");
        if(visibilityOfElementLocatedBylocator(DEFINITIONS,SECONDS20).isDisplayed()){
            scrollIntoViewByLocatorAndClick(LAST_DIV_OF_THE_DOCUMENT,"last div of the document");
            scrollIntoViewByLocatorAndClick(CLOSE_BUTTON,"[Close] button");
        }
    }

    /**
     * 5th Execution steps. Click Signup button.
     */
    public void clickSignupButton() throws InterruptedException {
        if(visibilityOfElementLocatedBylocator(SIGHN_UP_BUTTON,SECONDS20).isEnabled()){
            click(SIGHN_UP_BUTTON,"[SIGN UP] button");
            sleep(MILLISEC2000);
            if(driver.getCurrentUrl().endsWith("success")){
                assertEquals(driver.getCurrentUrl(), Parameters.instance().getUrl()+"signup/success");
                reportLog("Completely submitted your sign up request.");
            }else{
                reportLog("SOMETHING WENT WRONG.");
            }
        }
    }

    public void checkFinalResultLink(){
        invisibilityOfElementLocatedByLocator(SUCCESS_PAGE_HEADING,SECONDS20); //Wait until get to login page
        if(driver.getCurrentUrl().equals("https://"+GET_COMPANY_NAME.toLowerCase()+".int.staging.insly.training/login")){
            reportLog("You have successfully created an account");
            assertTrue(driver.getCurrentUrl().equals("https://"+GET_COMPANY_NAME.toLowerCase()+".int.staging.insly.training/login"));
        }else{
            reportLog(driver.getCurrentUrl());
            reportLog("UNABLE TO CREATE AN ACCOUNT. PLEASE CHECK AGAIN THE REQUEST");
        }
    }







}
