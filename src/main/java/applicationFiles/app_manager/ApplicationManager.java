package applicationFiles.app_manager;

import applicationFiles.framework.mainClass.Parameters;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class ApplicationManager {

    public static WebDriver driver;
    private final static boolean DEBUG = true;
    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String OS = System.getProperty("os.name").toLowerCase();
    public static Logger log = Logger.getLogger(ApplicationManager.class.getName());

    @Test(priority = 1)
    public void init() throws InterruptedException {
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
        // Disables the sandbox for all process types that are normally sandboxed (bypass OS security model_data) - this is
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

        driver.get("https://www.google.com/");

        if (driver.getPageSource().contains("I'm Feeling Lucky")) {
            reportLog("Pass");
        } else {
            reportLog("Fail");
        }
        
        reportLog(OS.toLowerCase());

        reportLog(driver.getTitle());

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
}
