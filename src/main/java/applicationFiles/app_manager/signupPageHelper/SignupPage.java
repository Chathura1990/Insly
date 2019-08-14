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
        Assert.assertEquals(title,"Google");
    }






}
