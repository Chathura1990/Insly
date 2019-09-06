package applicationFiles.testFiles.createLead;

import applicationFiles.app_manager.testBase.TestBase;
import org.testng.annotations.Test;

public class CreateLeadTest extends TestBase {

    @Priority(1)
    @Test(priority = 1)
    public void createLeadTest(){
        app.goTo().leadsPage();
        app.getLeadsPage().clickAddNewLeadButton();
        app.getLeadsPage().fillLeadForm();
    }


}

