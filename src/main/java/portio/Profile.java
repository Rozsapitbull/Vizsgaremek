package portio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Profile extends Pages{

    public Profile(WebDriver driver){
        super(driver);
    }

private final By INPUT_NAME= By.xpath("//input[@id='name']");
    private final By INPUT_BIO=By.xpath("//input[@id='bio']");
    private final By INPUT_PHONE_NUMBER=By.xpath("//input[@id='phone-number']");
    private final By BUTTON_SAVE_PROFILE = By.xpath("//button[@onclick='editUser()']");
    private final By BUTTON_PROFILE_DELETE=By.xpath("//button[@onclick='showRealDeleteAccBtn()']");
    private final By BUTTON_PROFILE_DELETE_VERIFICATION = By.xpath("//button[@onclick='deleteAccount()']");
    private final By ALERT_PROFILE_EDIT = By.xpath("//p[@id='edit-alert']");

    public void fillProfileUpdateForm(String name,String bio, String phoneNumber){
        driver.findElement(INPUT_NAME).sendKeys(name);
        driver.findElement(INPUT_BIO).sendKeys(bio);
        driver.findElement(INPUT_PHONE_NUMBER).sendKeys(phoneNumber);
    }
    public void clickUpdateProfileButton(){
        driver.findElement(BUTTON_SAVE_PROFILE).click();
    }
    public String getProfileAlertText(){
        return driver.findElement(ALERT_PROFILE_EDIT).getText();
    }

    public void deleteProfile(){
        driver.findElement(BUTTON_PROFILE_DELETE).click();
        driver.findElement(BUTTON_PROFILE_DELETE_VERIFICATION).click();
    }
}
