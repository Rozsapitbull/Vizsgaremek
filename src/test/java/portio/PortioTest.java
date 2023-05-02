package portio;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    @Story("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Accepting terms and conditions as a new user")
    public void termsAndConditionsTest_AsNewUser_Pass() throws InterruptedException {
        Login login = new Login(driver);
        login.navigate();
        Thread.sleep(1000);
        login.acceptTermsAndConditions();
        Thread.sleep(1000);
        Assertions.assertFalse(login.isTermsAndConditionsDisplayed());
    }

    @Test
    @Order(2)
    @Story("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Login with default username and password")
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
    @Order(3)
    @Story("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Login as newly registered user")
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
        login.login(username, password);
        Assertions.assertEquals("https://lennertamas.github.io/portio/landing.html", driver.getCurrentUrl());
    }

    @Test
    @Order(4)
    @Story("Authentication")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Login multiple times")
    public void loginTest_MultipleTimesWithValidCredentials_Pass() throws InterruptedException, IOException, ParseException {
        Login login = new Login(driver);
        login.navigate();
        Thread.sleep(1000);
        login.acceptTermsAndConditions();
        login.loginMultipleTimes();
        Assertions.assertEquals("https://lennertamas.github.io/portio/", driver.getCurrentUrl());
    }

    @Test
    @Order(5)
    @Story("Authentication")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Registration with valid credentials")
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
    @Order(6)
    @Story("Logout")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Logout as a logged in user")
    public void logoutTest_AsLoggedInUser_Pass() throws InterruptedException {
        Landing landing = new Landing(driver);
        Login login = new Login(driver);
        loginTest_ValidCredentials_Pass();
        landing.logout();
        Thread.sleep(1000);
        Assertions.assertTrue(login.isLoginFormDisplayed());
    }

    @Test
    @Order(7)
    @Story("Images")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Save image as logged in user")
    public void imageDownloadTest_AsLoggedInUser_ImageIsDownloaded() throws InterruptedException, IOException {
        Landing landing = new Landing(driver);
        loginTest_ValidCredentials_Pass();
        landing.savePortraitImage();
        Assertions.assertTrue(new File("src/test/java/temp/portrait.png").exists());
    }

    @Test
    @Order(8)
    @Story("Images")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Verification of logo display")
    public void logoTest_AsLoggedInUser_LogoIsDisplayed() throws InterruptedException {
        Landing landing = new Landing(driver);
        loginTest_ValidCredentials_Pass();
        Thread.sleep(1000);
        Assertions.assertTrue(landing.isLogoDisplayed());
    }

    @Test
    @Order(9)
    @Story("Images")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Verification of logo image")
    public void logoTest_AsLoggedInUser_LogoIsAccordingToTheExpected() throws InterruptedException, IOException {
        Landing landing = new Landing(driver);
        loginTest_ValidCredentials_Pass();
        BufferedImage logoOnSite = landing.getLogoImage();
        BufferedImage expected = ImageIO.read(new File("src/test/java/testData/logo.png"));
        ImageDiff diff = new ImageDiffer().makeDiff(logoOnSite, expected);
        Assertions.assertFalse(diff.hasDiff());
    }

    @Test
    @Order(10)
    @Story("Profile")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Update profile information")
    public void profileUpdateTest_AsLoggedInUser_ProfileIsUpdated() throws InterruptedException {
        Landing landing = new Landing(driver);
        Profile profile = new Profile(driver);
        loginTest_AsNewlyRegisteredUser_Pass();
        landing.openProfileSettings();
        String name = "Kispal";
        String bio = "BorzBorzBorz";
        String phoneNumber = "+36309999999";
        profile.fillProfileUpdateForm(name, bio, phoneNumber);
        Thread.sleep(1000);
        profile.clickUpdateProfileButton();
        Thread.sleep(1000);
        Assertions.assertEquals("Profile Edited!", profile.getProfileAlertText());
    }

    @Test
    @Order(11)
    @Story("Profile")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Delete account")
    public void profileDeletionTest_AsLoggedInUser_AccountIsDeleted() throws InterruptedException {
        Login login = new Login(driver);
        Landing landing = new Landing(driver);
        Profile profile = new Profile(driver);
        String username = "Tester";
        String password = "Test123";
        String email = "test12@example.com";
        String description = "TestTestTest.";
        login.navigate();
        Thread.sleep(1000);
        login.acceptTermsAndConditions();
        login.registration(username, password, email, description);
        login.login(username, password);
        landing.openProfileSettings();
        profile.deleteProfile();
        login.login(username, password);
        Assertions.assertEquals("Username or Password\n" + "is not correct!", login.getLoginMessage());
    }

    @Test
    @Order(12)
    @Story("Blog")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("All blog articles are displayed")
    public void blogTest_AsLoggedInUser_AllBlogArticlesAreDisplayed() throws InterruptedException, IOException {
        Landing landing = new Landing(driver);
        Blog blog = new Blog(driver);
        loginTest_ValidCredentials_Pass();
        landing.openBlogArticles();
        blog.saveArticleTitles();
        String expected = FileUtils.readFileToString(new File("src/test/java/testData/blogTitles.txt"), "UTF-8");
        String actual = FileUtils.readFileToString(new File("src/test/java/temp/actualBlogTitles.txt"), "UTF-8");
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }
}
