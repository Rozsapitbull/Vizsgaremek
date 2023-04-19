package portio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login extends Pages {
    public Login(WebDriver driver) {
        super(driver);
    }

    //@FindBy(xpath = "//input[@type='email']")
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
    private final By BUTTON_ACCEPT_TERMSANDCONDITIONS = By.xpath("//button[@id='terms-and-conditions-button']");
    private final By FORM_LOGIN = By.xpath("//div[@id='login']");
    private final By ALERT_REGISTRATION_SUCCESS = By.xpath("//p[@id='register-alert']");

    public void acceptTermsAndConditions() {
        if (driver.findElement(BUTTON_ACCEPT_TERMSANDCONDITIONS).isDisplayed()) {
            driver.findElement(BUTTON_ACCEPT_TERMSANDCONDITIONS).click();
        }
    }
    public void fillLoginForm(String username, String password) {
        driver.findElement(INPUT_LOGIN_USERNAME).sendKeys(username);
        driver.findElement(INPUT_LOGIN_PASSWORD).sendKeys(password);
    }
    public void fillRegisterForm(String username, String password,String email,String description){
        driver.findElement(INPUT_REGISTER_USERNAME).sendKeys(username);
        driver.findElement(INPUT_REGISTER_PASSWORD).sendKeys(password);
        driver.findElement(INPUT_REGISTER_EMAIL).sendKeys(email);
        driver.findElement(INPUT_REGISTER_DESCRIPTION).sendKeys(description);
    }

    public void login(String username, String password) {
        if (!(isLogin())) {
            driver.findElement(BUTTON_LOGIN_FORM).click();
        }
        fillLoginForm(username, password);
        driver.findElement((BUTTON_LOGIN)).click();
    }

    public void registration(String username, String password, String email, String description) throws InterruptedException {
        if (isLogin()) {
            driver.findElement(BUTTON_REGISTER_FORM).click();
        }
        Thread.sleep(1000);
        fillRegisterForm(username,password,email,description);
        driver.findElement(BUTTON_REGISTER).click();
    }

    public boolean isLogin() {
        return driver.findElement(FORM_LOGIN).isDisplayed();
    }

    public boolean isRegistrationSuccessful() {
        return driver.findElement(ALERT_REGISTRATION_SUCCESS).isDisplayed();
    }





}
