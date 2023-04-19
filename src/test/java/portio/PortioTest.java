package portio;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class PortioTest {
    private WebDriver driver;
    private static ChromeOptions options;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();

        options = new ChromeOptions();
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");

    }

    @BeforeEach
    public void init() {
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    @Story("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    public void loginTest_ValidCredentials_Pass() throws InterruptedException {
        Login login = new Login(driver);
        login.navigate();
        Thread.sleep(1000);
        login.acceptTermsAndConditions();
        String username = "lovasia";
        String password = "kispal123";
        login.login(username, password);
        Assertions.assertEquals("https://lennertamas.github.io/portio/landing.html", driver.getCurrentUrl());
    }


    @Test
    @Story("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    public void registrationTest_ValidCredentials_Pass() throws InterruptedException {
        Login login = new Login(driver);
        login.navigate();
        Thread.sleep(1000);
        login.acceptTermsAndConditions();
        String username = "Tester";
        String password = "Test123";
        String email = "test12@example.com";
        String description = "TestTestTest.";
        login.registration(username, password, email, description);
        Assertions.assertTrue(login.isRegistrationSuccessful());
    }

    @Test
    @Story("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    public void loginTest_AsNewlyRegisteredUser_Pass() throws InterruptedException {
        Login login = new Login(driver);
        login.navigate();
        Thread.sleep(1000);
        login.acceptTermsAndConditions();
        String username = "Tester";
        String password = "Test123";
        String email = "test12@example.com";
        String description = "TestTestTest.";
        login.registration(username, password, email, description);
        Thread.sleep(1000);
        //login.switchToLoginForm();
        login.login(username, password);
        Assertions.assertEquals("https://lennertamas.github.io/portio/landing.html", driver.getCurrentUrl());

    }

    @AfterEach
    public void tearDown() {
        driver.close();

    }
}
