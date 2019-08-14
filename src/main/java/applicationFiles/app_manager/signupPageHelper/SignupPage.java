package applicationFiles.app_manager.signupPageHelper;

import applicationFiles.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.WebDriver;

import static applicationFiles.app_manager.ApplicationManager.reportLog;

public class SignupPage extends SelectorService {

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    //Number of execution steps are according to the provided test suite.

    /**
     * 1st Execution steps. Check Website title, check web page Header
     */
    public void getPageTitle(){
        String title = driver.getTitle();
        reportLog("Sites Expected Title--> " +title);
    }






}
