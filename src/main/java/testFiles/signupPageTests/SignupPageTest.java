package testFiles.signupPageTests;

import applicationFiles.app_manager.model_data.SignupPageData;
import applicationFiles.app_manager.test_base.TestBase;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static applicationFiles.app_manager.ApplicationManager.log;
import static applicationFiles.app_manager.ApplicationManager.reportLog;

public class SignupPageTest extends TestBase {

    private SignupPageData spd = new SignupPageData();

    @Test(priority = 1)
    public void completeSignupPage() throws InterruptedException {
        log.info("");
        reportLog("****** Fill Company Block. *****");
        app.getSignupPage().getPageTitle();
        app.getSignupPage().checkPageHeader();
        app.getSignupPage().fillCompanyBlockForm(spd.setCompanyName(RandomStringUtils.randomAlphabetic(6,10)));
        log.info("");
        reportLog("****** Fill Administrator Account Details Block *****");
        app.getSignupPage().fillAdminAccountDetailsForm(spd
                .setAdminEmail(RandomStringUtils.randomAlphabetic(7).toLowerCase() + "@email.com")
                .setAdminName(RandomStringUtils.randomAlphabetic(6,11)+" "+RandomStringUtils.randomAlphabetic(6,10))
                .setAdminPhone(app.getSignupPage().getPhoneCountryCode() + RandomStringUtils.randomNumeric(9)));
        log.info("");
        reportLog("****** Fill Terms And Conditions Block And Click Sign Up Button *****");
        app.getSelectors().scrollUpOrDown(300);
        app.getSignupPage().clickCheckBoxesAndReadPrivacyPolicy();
        app.getSignupPage().clickSignupButton();
        app.getSignupPage().checkFinalResultLink();
    }
}
