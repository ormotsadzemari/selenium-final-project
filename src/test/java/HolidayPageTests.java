import ge.tcb.testacademy.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class HolidayPageTests extends BaseTest {


    @Test
    void descendingOrderTest() {

        goToDasveneba();

        String currentURL = driver.getCurrentUrl();

        Assert.assertEquals(currentURL, Constants.SWOOP_DASVENEBA);

        Map<String, Double> allOffers = allOffers();

        Map<String, Double> sortedOffers = allOffers.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));


        Map.Entry<String, Double> mostExpensiveOne = sortedOffers.entrySet()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No offers so far"));

        //Assert.assertEquals(allOffers.size(), Constants.NUMBER_OF_OFFERS); seems like it changes time to time so not reliable(did i spell it right?)


        clickByXpath(Constants.SHESABAMISOBIT_XPATH); //clicks on შესაბამისობა

        clickByXpath(Constants.KLEBADI_XPATH); // clicks on დალაგება კლებადობით

        WebElement firstInLine = getFirstOffer();

        Assert.assertEquals(mostExpensiveOne.getKey(), firstInLine.getAttribute("href"));


    }

    @Test
    public void ascendingOrderTest() {


        goToDasveneba();

        String currentURL = driver.getCurrentUrl();

        Assert.assertEquals(currentURL, Constants.SWOOP_DASVENEBA);

        Map<String, Double> allOffers = allOffers();

        Map<String, Double> sortedOffers = allOffers.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()) // asc
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        Map.Entry<String, Double> leastExpensiveOne = sortedOffers.entrySet()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No offers so far"));


        clickByXpath(Constants.SHESABAMISOBIT_XPATH); //clicks on შესაბამისობა

        clickByXpath(Constants.ZRDADI_XPATH); // clicks on დალაგება ზრდადობით

        WebElement firstInLine = getFirstOffer();

        Assert.assertEquals(leastExpensiveOne.getKey(), firstInLine.getAttribute("href"));

    }

    @Test
    public void filterTest() {
        goToDasveneba();
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, Constants.SWOOP_DASVENEBA);

        clickByXpath("//a[contains(@href, '/category/2045/dasveneba/mtis-kurortebi')]");

        clickByXpath("//label[@for='radio-გადახდის ტიპი-1']");

        WebElement fullPaymentTag = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'items-center') and contains(., 'სრული გადახდა')]//p[contains(text(), 'სრული გადახდა')]")
        ));
        Assert.assertTrue(fullPaymentTag.isDisplayed(), "'სრული გადახდა' tag is not displayed above the list of offers");

        clickByXpath(Constants.SHESABAMISOBIT_XPATH); //clicks on შესაბამისობა

        clickByXpath(Constants.ZRDADI_XPATH); // clicks on დალაგება ზრდადობით


        Map<String, Double> allOffers = allOffers();

        Map<String, Double> sortedOffers = allOffers.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()) // asc
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        Map.Entry<String, Double> leastExpensiveOne = sortedOffers.entrySet()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No offers so far"));


        WebElement firstInLine = getFirstOffer();

        Assert.assertEquals(leastExpensiveOne.getKey(), firstInLine.getAttribute("href"));


    }

    @Test
    public void pricingRangeTest() {
        goToDasveneba();
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, Constants.SWOOP_DASVENEBA);

        clickByXpath("//p[text()='0₾ - 100₾']/parent::div");


        Map<String, Double> allOffers = allOffers();

        boolean allInRange = allOffers.values().stream().allMatch(price -> price >= 0 && price <= 100);

        Assert.assertTrue(allInRange);


    }

    @Test
    public void activeCategoryTest() {
        Actions actions = new Actions(driver);

        WebElement categoriesButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid='outline-button']")));


        categoriesButton.click();


        WebElement sportCategory = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, '/category/110/sporti/')]")));
        actions.moveToElement(sportCategory).perform();

        WebElement kartingCategory = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(), 'კარტინგი')]")));
        kartingCategory.click();


        wait.until(ExpectedConditions.urlContains("kartingi"));

        String expected1=Constants.KARTINGI;
        String expected2=Constants.KARTINGI_WITHOUT_WWW;
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.equals(expected1) || actualUrl.equals(expected2));


        WebElement categoryChain = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("nav.flex.items-center.flex-nowrap.whitespace-nowrap.py-2")
        ));

        String categoryChainText = categoryChain.getText();

        Assert.assertTrue(categoryChainText.contains("კარტინგი"));


    }


    @Test
    public void logoTest() {
        goToDasveneba();

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(Constants.LOGO)
        ));
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.click();


        wait.until(ExpectedConditions.urlToBe(Constants.SWOOP));
        String currentURL = driver.getCurrentUrl();

        Assert.assertEquals(currentURL, Constants.SWOOP);

    }

}


