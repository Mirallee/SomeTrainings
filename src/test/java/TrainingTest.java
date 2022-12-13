import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.Iterator;
import java.util.Set;

public class TrainingTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        //validate "This is a demo website, which act as a mock site for trying out different automation tools"
        //is visible on the page

        driver.get("https://demosite.executeautomation.com/index.html");
        WebElement firstMessage = driver.findElement(By.cssSelector("body p:nth-child(1)"));
        Assert.assertTrue(firstMessage.isDisplayed());

        //Validate needed information is working correctly

        WebElement staticDropDown = driver.findElement(By.id("TitleId"));
        Select dropDown = new Select(staticDropDown);
        dropDown.selectByVisibleText("Mr.");
        driver.findElement(By.id("FirstName")).sendKeys("Patrick");
        WebElement femaleButton = driver.findElement(By.xpath("//input[@value='female']"));
        femaleButton.click();
        Assert.assertTrue(femaleButton.isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//input[@name='english']")).isSelected());

        //Validate popup window is working correctly

        driver.findElement(By.xpath("//a[@href='popup.html']")).click();
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> it = windows.iterator();
        String parentWindow = it.next();
        String childWindow = it.next();
        driver.switchTo().window(childWindow);
        WebElement message = driver.findElement(By.cssSelector("body p:nth-child(1)"));
        Assert.assertTrue(message.isDisplayed());
        WebElement secondStaticDropDown = driver.findElement(By.cssSelector("#TitleId"));
        Select secondDropDown = new Select(secondStaticDropDown);
        secondDropDown.selectByVisibleText("Mr.");
        driver.findElement(By.cssSelector("#FirstName")).sendKeys("Patricia");
        driver.findElement(By.xpath("//input[@name='LastName']")).sendKeys("koza");
        driver.findElement(By.cssSelector("input[value='female']")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("input[value='male']")).isSelected());
        WebElement countryStaticDropDown = driver.findElement(By.xpath("//select[@id='Country']"));
        Select countryDropDown = new Select(countryStaticDropDown);
        countryDropDown.selectByIndex(2);
        driver.switchTo().window(parentWindow);

        //validate parentWindow is displayed

        Assert.assertTrue(firstMessage.isDisplayed());

        //validate JavaScript alert is working after click

        driver.findElement(By.xpath("//input[@name='generate']")).click();
        Alert alert = driver.switchTo().alert();
        for(int i = 0; i<2; i++ ) {
            alert.accept();
        }




    }
}
