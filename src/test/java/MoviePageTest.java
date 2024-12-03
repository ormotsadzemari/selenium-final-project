import ge.tcb.testacademy.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;


public class MoviePageTest extends BaseTest {

    @Test
    public void goToKino(){
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/movies/']")));
        element.click();
        wait.until(ExpectedConditions.urlToBe(Constants.KINO));
        String currentUrl=driver.getCurrentUrl();
        Assert.assertEquals(currentUrl,Constants.KINO);

    }
//
//    @Test
//    public void firstMovie(){
//        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/movies/']")));
//        element.click();
//        wait.until(ExpectedConditions.urlToBe(Constants.KINO));
//
//    }


}
