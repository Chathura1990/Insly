package applicationFiles.app_manager.testBase;

import applicationFiles.app_manager.selectorHelper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static applicationFiles.app_manager.ApplicationManager.*;
import static applicationFiles.framework.globalParameters.GlobalParameters.*;
import static org.openqa.selenium.By.*;
import static org.testng.Assert.assertEquals;

public class SessionHelper extends SelectorService {

    private By UserName = id("username");
    private By Password = id("password");
    private By SubmitButton = id("Login");

    public SessionHelper(WebDriver driver) {
        super(driver);
    }

    public void login_To_Website() {
        log.info("");
        type(UserName, VALID_USERNAME);
        type(Password, VALID_PASSWORD);
        click(SubmitButton, "[Login] button");

        if (driver.getCurrentUrl().equals("https://ap15.lightning.force.com/lightning/page/home")) {
            reportLog("You have entered valid credentials");
        } else {
            reportLog("Please Check your Credentials and try to signin again");
            assertEquals(driver.getCurrentUrl(), "https://ap15.lightning.force.com/lightning/page/home");
            }

        log.info("");
    }
}
