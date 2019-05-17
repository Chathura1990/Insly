package applicationFiles.app_manager.selector_helper;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static applicationFiles.app_manager.ApplicationManager.*;
import static applicationFiles.framework.global_parameters.GlobalParameters.*;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class SelectorService {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public SelectorService(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * this method makes WebDriver poll the DOM for a certain amount of time when trying to locate an element.
     * @param units
     */
    public void implicit_Wait(int units) {
        driver.manage().timeouts().implicitlyWait(units, TimeUnit.SECONDS);
    }

    protected WebElement visibilityOfElementLocatedBylocator(By locator, int unit) //Visibility Of Element Located By Xpath
    {
        wait = new WebDriverWait(driver, unit);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Boolean invisibilityOfElementLocatedByLocator(By locator, int unit)//Invisibility Of Element Located By Xpath
    {
        wait = new WebDriverWait(driver, unit);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void scrollIntoViewByLocatorAndClick(By locator, String text) {
        WebElement locator1 = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", locator1);
        locator1.click();
        reportLog("Clicked [" + text + "] using element: (" + locator + ")");
    }

    public void scrollUpOrDown(int value) throws InterruptedException {
        ((JavascriptExecutor)driver).executeScript("scroll(0,"+value+")");
        sleep(LONG_WAIT*2);
    }

    protected void waitElementToBeClickable(By locator) {
        wait = new WebDriverWait(driver, SECONDS20);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        reportLog("Wait element to be clickable: (" + locator + ")");
    }

    protected void click(By locator, String text) {
        visibilityOfElementLocatedBylocator(locator, SECONDS20);
        waitElementToBeClickable(locator);
        if (isELementPresent(locator)) {
            driver.findElement(locator).click();
        }
        reportLog("Clicked '" + text + "' using element: (" + locator + ")");
    }

    public void type(By locator, String text) {//Click on field,clear field and enter the text
        visibilityOfElementLocatedBylocator(locator, SECONDS20);
        driver.findElement(locator).click();
        if (text != null) {
            (new Actions(driver)).moveToElement(driver.findElement(locator))
                    .sendKeys(Keys.chord(Keys.CONTROL + "a"))
                    .sendKeys(Keys.BACK_SPACE)
                    .build().perform();
            driver.findElement(locator).sendKeys(text);
            reportLog("Typed: '" + text + "' in the field: (" + locator + ")");
        }
    }
    protected String getText(By locator) {
        implicit_Wait(SECONDS20);
        return visibilityOfElementLocatedBylocator(locator, SECONDS20).getText();
    }

    public int randomNumb(int min, int max){
        return (int)(Math.random()*((max-min)+1))+min;
    }

    protected boolean selectAnOptionFromDropdown(By locator, int option){
        Select select = new Select(driver.findElement(locator));
        List<WebElement> options = select.getOptions();
        String optionText = options.get(option).getText();
        if (!optionText.isEmpty()) {
            reportLog("Selected option: '" + optionText + "'");
            options.get(option).click();
            return true;
        }else {
            return false;
        }
    }

    protected boolean isELementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    protected void checkImageIsAvailableOrNot(By locator1, String text1, String text2) {
        WebElement image = visibilityOfElementLocatedBylocator(locator1, SECONDS20);
        reportLog("wait for the element (" + text1 + ")");

        Object result = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && " + "typeof arguments[0].naturalWidth != \"undefined\" && " + "arguments[0].naturalWidth > 0", image);

        boolean loaded = false;
        if (result instanceof Boolean) {
            loaded = (Boolean) result;
            reportLog("'" + text2 + "' image is available -->" + " " + loaded);
        }
    }

    public void checkAnchorTagLinkStatus(By locator, String value) throws IOException {
        WebElement link = driver.findElement(locator);
        HttpURLConnection connection = (HttpURLConnection) new URL(link.getAttribute("href")).openConnection();
        connection.setConnectTimeout(LONG_WAIT);
        connection.setInstanceFollowRedirects( false );
        connection.connect();
        int code = connection.getResponseCode();
        connection.disconnect();

        if(code == 200){
            reportLog("Link: "+link+" code status is "+code);
        }else{
            reportLog("This link is corrupted: "+ link);
        }
    }
}
