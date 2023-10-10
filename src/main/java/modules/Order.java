package modules;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.*;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.AppiumDriverFactory;

import java.time.Duration;

public class Order {

    AppiumDriver driver;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"username_textfield\"]/android.widget.EditText")
    //@iOSXCUITFindBy(xpath = " ")
    private WebElement e;

    public Order() {
        driver = AppiumDriverFactory.getDriver();
    }

    public void userLoginWithAndCreds(String username, String password) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(username + "----" + password);

        e.click();
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"username_textfield\"]/android.widget.EditText")).sendKeys(username);
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"password_textfield\"]/android.widget.EditText")).click();
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"password_textfield\"]/android.widget.EditText")).sendKeys(password);
        //driver.findElement(By.xpath("//android.view.View[@content-desc=\"Login Button\"]")).click();
    }
}
