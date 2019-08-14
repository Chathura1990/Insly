package testFiles.signupPageTests;

import applicationFiles.app_manager.model_data.SignupPageData;
import applicationFiles.app_manager.test_base.TestBase;
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
    }
}
