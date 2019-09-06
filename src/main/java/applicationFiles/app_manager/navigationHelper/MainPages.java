package applicationFiles.app_manager.navigationHelper;

import applicationFiles.app_manager.selectorHelper.SelectorService;
import applicationFiles.framework.globalParameters.Parameters;
import org.openqa.selenium.WebDriver;

import static applicationFiles.app_manager.ApplicationManager.reportLog;

public class MainPages extends SelectorService {

    public MainPages(WebDriver driver) {
        super(driver);
    }

    public void leadsPage() {
        driver.get(Parameters.instance().getUrl() + "/lightning/o/Lead/list?filterName=Recent");
        reportLog("logged into home page");
    }

}
