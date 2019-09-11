package applicationFiles.testFiles.createLead;


import applicationFiles.app_manager.testBase.SessionHelper;
import applicationFiles.app_manager.testBase.TestBase;
import org.testng.annotations.Test;

import static applicationFiles.app_manager.ApplicationManager.*;

public class CreateLeadTest extends TestBase {

    @Priority(1)
    @Test(priority = 1)
    public void createLeadTest(){
        new SessionHelper(driver).login_To_Website();
    }


}

