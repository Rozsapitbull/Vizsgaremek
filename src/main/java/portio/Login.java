package portio;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Login extends Pages {
    public Login(WebDriver driver) {
        super(driver);
    }

    private final By INPUT_LOGIN_USERNAME = By.xpath("//input[@id='email']");
    private final By INPUT_LOGIN_PASSWORD = By.xpath("//input[@id='password']");
    private final By INPUT_REGISTER_USERNAME = By.xpath("//input[@id='register-username']");
    private final By INPUT_REGISTER_PASSWORD = By.xpath("//input[@id='register-password']");
    private final By INPUT_REGISTER_EMAIL = By.xpath("//input[@id='register-email']");
    private final By INPUT_REGISTER_DESCRIPTION = By.xpath("//input[@id='register-description']");
    private final By BUTTON_LOGIN = By.xpath("//button[@onclick='myFunction()' and text()=\"Login\"]");
    private final By BUTTON_REGISTER = By.xpath("//button[@onclick='registerUser()' and text()=\"Register\"]");
    private final By BUTTON_LOGIN_FORM = By.xpath("//div[@id='register']/button[@id='login-form-button' and @onclick='showLogin()']");
    private final By BUTTON_REGISTER_FORM = By.xpath("//div[@id='login']/button[@id='register-form-button' and @onclick='showRegister()']");
    private final By BUTTON_ACCEPT_TERMS_AND_CONDITIONS = By.xpath("//button[@id='terms-and-conditions-button']");
    private final By FORM_LOGIN = By.xpath("//div[@id='login']");
    private final By ALERT_REGISTRATION_SUCCESS = By.xpath("//p[@id='register-alert']");
    private final By ALERT_LOGIN_FAIL = By.xpath("//p[@id='alert']");

    public void acceptTermsAndConditions() {
        if (driver.findElement(BUTTON_ACCEPT_TERMS_AND_CONDITIONS).isDisplayed()) {
            driver.findElement(BUTTON_ACCEPT_TERMS_AND_CONDITIONS).click();
        }
    }

    public void fillLoginForm(String username, String password) {
        driver.findElement(INPUT_LOGIN_USERNAME).sendKeys(username);
        driver.findElement(INPUT_LOGIN_PASSWORD).sendKeys(password);
    }

    public void fillRegisterForm(String username, String password, String email, String description) {
        driver.findElement(INPUT_REGISTER_USERNAME).sendKeys(username);
        driver.findElement(INPUT_REGISTER_PASSWORD).sendKeys(password);
        driver.findElement(INPUT_REGISTER_EMAIL).sendKeys(email);
        driver.findElement(INPUT_REGISTER_DESCRIPTION).sendKeys(description);
    }

    public void login(String username, String password) {
        if (!(isLoginFormDisplayed())) {
            driver.findElement(BUTTON_LOGIN_FORM).click();
        }
        fillLoginForm(username, password);
        driver.findElement((BUTTON_LOGIN)).click();
    }
public void loginMultipleTimes()throws IOException, ParseException{
        Map<String,String> credentials = createCredentialsMap();
        for (String key:credentials.keySet()){
            login(key,credentials.get(key));
            navigate();
        }

}



    public void registration(String username, String password, String email, String description) throws InterruptedException {
        if (isLoginFormDisplayed()) {
            driver.findElement(BUTTON_REGISTER_FORM).click();
        }
        Thread.sleep(1000);
        fillRegisterForm(username, password, email, description);
        driver.findElement(BUTTON_REGISTER).click();
    }

    public boolean isTermsAndConditionsDisplayed() {
        return driver.findElement(BUTTON_ACCEPT_TERMS_AND_CONDITIONS).isDisplayed();
    }

    public boolean isLoginFormDisplayed() {
        return driver.findElement(FORM_LOGIN).isDisplayed();
    }

    public boolean isRegistrationSuccessful() {
        return driver.findElement(ALERT_REGISTRATION_SUCCESS).isDisplayed();
    }

    public String getLoginMessage() {
        return driver.findElement(ALERT_LOGIN_FAIL).getText();
    }
    public Map<String,String> createCredentialsMap() throws IOException, ParseException {
        Map<String, String> credentials = new HashMap<>();
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader("src/test/java/testData/credentials.json"));
        JSONArray loginData = (JSONArray) object;

        for (Object Data : loginData) {
            String userName = ((JSONObject) Data).get("userName").toString();
            String pw = ((JSONObject) Data).get("pw").toString();
            credentials.put(userName, pw);
        }
        return credentials;
    }


}
