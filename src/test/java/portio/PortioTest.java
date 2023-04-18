package portio;

import io.github.bonigarcia.wdm.WebDriverManager;
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
        //options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");

    }

    @BeforeEach
    public void init() {
        //WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }


    @Test
    public void loginTest_valid_pass()throws InterruptedException {
        Login login = new Login(driver);
        login.navigate();
        Thread.sleep(1000);
        String username = "lovasia";
        String password = "kispal123";
        login.login(username, password);
        Assertions.assertEquals("https://lennertamas.github.io/portio/landing.html", driver.getCurrentUrl());
    }



    @Test
    public void registrationTest() throws InterruptedException {
        Login login = new Login(driver);
        login.navigate();
        Thread.sleep(1000);
        String username = "Tester";
        String password = "Test123";
        String email = "test12@example.com";
        String description = "TestTestTest.";
        login.Registration(username, password, email, description);
        Assertions.assertTrue(login.isRegistrationSuccessful());
    }

    @AfterEach
    public void tearDown() {
        driver.close();

    }
}
