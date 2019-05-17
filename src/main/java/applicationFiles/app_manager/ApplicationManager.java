package applicationFiles.app_manager;

import applicationFiles.app_manager.selector_helper.SelectorService;
import applicationFiles.app_manager.signupPageHelper.SignupPage;
import applicationFiles.framework.mainClass.Parameters;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static applicationFiles.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;

public class ApplicationManager {

    public static WebDriver driver;
    private final static boolean DEBUG = true;
    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String OS = System.getProperty("os.name").toLowerCase();
    public static Logger log = Logger.getLogger(ApplicationManager.class.getName());
    private SignupPage signupPage;
    private SelectorService selectors;

    public void init() {
        /*
         * open browser (GoogleChrome) and enter user credentials
         */
        ChromeOptions chromeOptions = new ChromeOptions();
        // Prevent infobars from appearing.
        chromeOptions.addArguments("--disable-infobars");
        // Disable extensions.
        chromeOptions.addArguments("--disable-extensions");
        // Disables GPU hardware acceleration. If software renderer is not in place, then the GPU process won't launch.
        chromeOptions.addArguments("--disable-gpu");
        // Disables the sandbox for all process types that are normally sandboxed (bypass OS security model_data) - this is
        // necessary within the Docker environment otherwise you will get "NoSuchSession" exception
        chromeOptions.addArguments("--no-sandbox");
        // Disables the use of a zygote process for forking child processes. Instead, child processes will be forked and
        // exec'd directly. Note that --no-sandbox should also be used together with this flag because the sandbox needs the
        // zygote to work.
        chromeOptions.addArguments("--no-zygote");
        // Overcome limited resource problems
        chromeOptions.addArguments("--disable-dev-shm-usage");

        if (OS.startsWith("windows")) {
            System.setProperty("webdriver.chrome.driver", Parameters.instance().getChromeDriverWin());
        } else if (OS.startsWith("linux")) {
            System.setProperty("webdriver.chrome.driver", Parameters.instance().getChromeDriverLin());
            if (Parameters.instance().getHeadless().toLowerCase().equals("true")) {
                chromeOptions.addArguments("--headless");
            }
        }
        // Set max. dimensions of the browser window
        chromeOptions.addArguments("window-size=1920,1080");

        driver = new ChromeDriver(chromeOptions);

        long start = System.currentTimeMillis();
        driver.get(Parameters.instance().getUrl()); //Opening the Staging site https://signup.int.staging.insly.training/
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.MILLISECONDS);
        long finish = System.currentTimeMillis();
        long totalTimeInMillis = finish - start;
        double seconds = (totalTimeInMillis / 1000.0) % 60;
        double minutes = (double) ((totalTimeInMillis / (1000 * 60)) % 60);
        reportLog("Total time to load the page -> " + "milliseconds: " + totalTimeInMillis + " minutes:" + minutes + " seconds:" + seconds);
        signupPage = new SignupPage(driver);
        selectors = new SelectorService(driver);
    }

    public void stop() throws InterruptedException {
        sleep(LONG_WAIT);
        driver.quit();
    }

    //Method for adding logs passed from test cases
    public static String reportLog(String message) {
        if (DEBUG) {
            Reporter.setEscapeHtml(false);
            Date date = new Date();
            log.info("-- " + message);
            Reporter.log(dateFormat.format(date) + " /" + " " + message);
        }
        return message;
    }

    public SignupPage getSignupPage(){ return signupPage; }

    public SelectorService getSelectors(){ return selectors; }
}
