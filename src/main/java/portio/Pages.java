package portio;

import org.openqa.selenium.WebDriver;


public class Pages {
    WebDriver driver;
    private final String URL = "https://lennertamas.github.io/portio/";

    public Pages(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() {
        driver.navigate().to(URL);
    }

}
