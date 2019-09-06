package applicationFiles.app_manager.salesforcePageHelper;

import applicationFiles.app_manager.selectorHelper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static applicationFiles.framework.globalParameters.GlobalParameters.*;
import static org.openqa.selenium.By.*;

public class LeadsPage extends SelectorService {

    //lead page buttons
    private By addNewButton = xpath("//div[@title='New']");
    //new Lead model
    private By newLeadTitle = xpath("//h2[contains(text(),'New Lead')]");
    private By salutationDropdown = xpath("//div[@class='salutation compoundTLRadius compoundTRRadius compoundBorderBottom form-element__row uiMenu']");
    private By chooseSalutation = xpath("//li[@class='uiMenuItem uiRadioMenuItem']/a[@title='Mr.']");

    public LeadsPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddNewLeadButton(){
        click(addNewButton,"[New] button");
    }

    public void fillLeadForm(){
        if(driver.findElement(newLeadTitle).isDisplayed()) {
            click(salutationDropdown, "[Salutaion] dropdown");
            click(chooseSalutation, "[Mr.]");
        }

    }


}
