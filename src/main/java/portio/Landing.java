package portio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Landing extends Pages {


    public Landing(WebDriver driver) {
        super(driver);
    }


    private final By BUTTON_LOGOUT = By.xpath("//a[@onclick='logout()']");
    private final By BUTTON_PROFILE = By.xpath("//p[@id='profile-btn']");
    private final By BUTTON_CONTACT = By.xpath("//a[@href='https://lennertamas.github.io/portio/contact']");
    private final By BUTTON_BLOG = By.xpath("//a[@href='https://lennertamas.github.io/portio/#blog']");
    private final By BUTTON_SEE_ALL_POSTS = By.xpath("//a[@class='btn btn-outline-secondary  btn-zoom m-3' and @href='https://lennertamas.github.io/portio/blog']");
    private final By IMG_LOGO = By.xpath("//img[@alt='site-logo']");
    private final By IMG_PORTRAIT = By.xpath("//img[@alt='hero-image']");



    public void logout() {
        driver.findElement(BUTTON_LOGOUT).click();
    }


    public void savePortraitImage()throws IOException{
        ImageIO.write(getPicture(IMG_PORTRAIT),"png",new File("src/test/java/temp/portrait.png"));
    }
    public void openProfileSettings(){
        driver.findElement(BUTTON_PROFILE).click();
    }
    public void openBlogArticles()throws InterruptedException{
        driver.findElement(BUTTON_BLOG).click();
        Thread.sleep(1000);
        driver.findElement(BUTTON_SEE_ALL_POSTS).click();
    }
    public void clickContactButton(){
        driver.findElement(BUTTON_CONTACT).click();
    }

    public boolean isLogoDisplayed(){
        return driver.findElement(IMG_LOGO).isDisplayed();
    }
    public BufferedImage getLogoImage()throws IOException{
        return getPicture(IMG_LOGO);
    }
    public BufferedImage getPicture(By by)throws IOException {
        URL imgURL = new URL(driver.findElement(by).getAttribute("src"));
        return ImageIO.read(imgURL);
    }




}