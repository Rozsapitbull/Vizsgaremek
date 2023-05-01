package portio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Blog extends Pages {
    public Blog(WebDriver driver) {
        super(driver);
    }

    private final By LIST_BLOG_ARTICLE_TITLES = By.xpath("//h5[@class='mb-0']");
    private final By BUTTON_NEXT_PAGE = By.xpath("//a[@rel='next']");

    public void saveArticleTitles() throws IOException {
        String titles = gatherArticleTitles();
        File file = new File("src/test/java/temp/actualBlogTitles.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(titles);

        writer.close();
    }

    public String gatherArticleTitles() {
        StringBuilder sb = new StringBuilder();
        while (isThereNextPage()) {
            List<WebElement> titles = new ArrayList<>(driver.findElements(LIST_BLOG_ARTICLE_TITLES));
            for (WebElement title : titles) {
                sb.append(title.getText())
                        .append("\n");

            }
            driver.findElement(BUTTON_NEXT_PAGE).click();
        }
        List<WebElement> titles = new ArrayList<>(driver.findElements(LIST_BLOG_ARTICLE_TITLES));
        for (WebElement title : titles) {
            sb.append(title.getText())
                    .append("\n");
        }
        return sb.toString();
    }

    public boolean isThereNextPage() {
        try {
            return driver.findElement(BUTTON_NEXT_PAGE).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
