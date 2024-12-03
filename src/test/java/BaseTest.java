import ge.tcb.testacademy.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(Constants.SWOOP);

        driver.manage().window().maximize();

        try {
            WebElement acceptCookiesButton = driver.findElement(By.xpath("//button//p[text()='ვეთანხმები']"));
            acceptCookiesButton.click();
        } catch (Exception e) {
            System.out.println("No Cookies :'( .");
        }
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void goToDasveneba() {


        WebElement dasveneba = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a//p[text()='დასვენება']")));

        dasveneba.click();


        wait.until(ExpectedConditions.urlContains(Constants.SWOOP_DASVENEBA));
    }

    protected Map<String, Double> allOffers() {
        Map<String, Double> allOffer = new HashMap<>();

        boolean hasNext = true;
        while (hasNext) {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(Constants.OFFERS_XPATH)));

            List<WebElement> thisOffer = driver.findElements(By.cssSelector(Constants.OFFERS_XPATH));


            for (WebElement offer : thisOffer) {

                String priceText = offer.findElement(By.xpath(".//h4[contains(@class, 'font-tbcx-bold')]")).getText();
                double price = Double.parseDouble(priceText.replace("₾", "").trim());
                String offerUrl = offer.getAttribute("href");
                allOffer.put(offerUrl, price);


            }

            try {
                WebElement nextPageButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='flex h-9 w-9 items-center justify-center rounded-lg cursor-pointer hover:bg-secondary_gray-10-value transition-colors duration-300']//img[@src='/icons/ep_arrow-right-bold.svg']")
                ));
                if (nextPageButton.isEnabled()) {
                    nextPageButton.click();
                    wait.until(ExpectedConditions.stalenessOf(thisOffer.get(0)));
                } else {
                    hasNext = false;
                }
            } catch (Exception e) {
                hasNext = false; //no more pages
            }
        }

        return allOffer;
    }

    protected WebElement getFirstOffer() {

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(Constants.OFFERS_XPATH)));
        List<WebElement> offers = driver.findElements(By.cssSelector(Constants.OFFERS_XPATH));
        if (offers.isEmpty()) {
            throw new NoSuchElementException("No offers found");
        }
        return offers.get(0);
    }

    protected void clickByXpath(String xpath) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(xpath)
        ));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.click();

//        wait.until(ExpectedConditions.stalenessOf(element));

    }
}

