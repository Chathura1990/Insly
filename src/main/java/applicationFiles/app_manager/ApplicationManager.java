package applicationFiles.app_manager;

import applicationFiles.app_manager.navigationHelper.MainPages;
import applicationFiles.app_manager.salesforcePageHelper.LeadsPage;
import applicationFiles.app_manager.selectorHelper.SelectorService;
import applicationFiles.app_manager.testBase.SessionHelper;
import applicationFiles.framework.globalParameters.Parameters;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static applicationFiles.framework.globalParameters.GlobalParameters.*;
import static java.lang.Thread.sleep;

public class ApplicationManager {

    public static WebDriver driver;
    private final static boolean DEBUG = true;
    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String OS = System.getProperty("os.name").toLowerCase();
    public static Logger log = Logger.getLogger(ApplicationManager.class.getName());

    private SelectorService selectorService;
    private MainPages mainPages;
    private LeadsPage leadsPage;

    public void init() {
        /*
         * open browser (GoogleChrome) and enter user credentials
         */
        ChromeOptions chromeOptions = new ChromeOptions();
        // Prevent infobars from appearing.
        chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
        chromeOptions.addArguments("--disable-infobars");
        // Disable extensions.
        chromeOptions.addArguments("--disable-extensions");
        // Disables GPU hardware acceleration. If software renderer is not in place, then the GPU process won't launch.
        chromeOptions.addArguments("--disable-gpu");
        // Disables the sandbox for all process types that are normally sandboxed (bypass OS security modelData) - this is
        // necessary within the Docker environment otherwise you will get "NoSuchSession" exception
        chromeOptions.addArguments("--no-sandbox");
        // Disables the use of a zygote process for forking child processes. Instead, child processes will be forked and
        // exec'd directly. Note that --no-sandbox should also be used together with this flag because the sandbox needs the
        // zygote to work.
//        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--no-zygote");
        // Overcome limited resource problems
        chromeOptions.addArguments("--disable-dev-shm-usage");

        if (OS.startsWith("windows")) {
            System.setProperty("webdriver.chrome.driver", Parameters.instance().getChromeDriverWin());
        } else if (OS.startsWith("linux")) {
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
                chromeOptions.addArguments("--headless");
        }
        // Set max. dimensions of the browser window
        chromeOptions.addArguments("window-size=1920,1080");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().pageLoadTimeout(SECONDS20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);

        long start = System.currentTimeMillis();
        driver.get(Parameters.instance().getUrl()); //Opening the Salesforce site
        long finish = System.currentTimeMillis();
        long totalTimeInMillis = finish - start;
        double seconds = (totalTimeInMillis / 1000.0) % 60;
        double minutes = (double) ((totalTimeInMillis / (1000 * 60)) % 60);
        reportLog("Total time to load the page -> " + "milliseconds: " + totalTimeInMillis + " minutes:" + minutes + " seconds:" + seconds); //Counting time to open the page
        selectorService = new SelectorService(driver);
        mainPages = new MainPages(driver);
        leadsPage = new LeadsPage(driver);
        new SessionHelper(driver).login_To_Website();
    }

    @AfterTest
    public void stop() throws InterruptedException {
        sleep(4000);
        driver.quit();
    }

    //Method for adding logs passed from test cases
    public static void reportLog(String message) {
        if (DEBUG) {
            Reporter.setEscapeHtml(false);
            Date date = new Date();
            log.info("-- " + message);
            Reporter.log(dateFormat.format(date) + " /" + " " + message);
        }
    }

    public SelectorService getSelectorService(){ return selectorService; }

    public MainPages goTo() { return mainPages; }

    public LeadsPage getLeadsPage() { return leadsPage; }
}
