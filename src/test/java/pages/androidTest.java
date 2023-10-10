package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.Dimension;
import utilities.AppiumDriverFactory;

import java.awt.*;
import java.time.Duration;

import static io.appium.java_client.touch.offset.PointOption.point;

public class androidTest {

    AppiumDriver driver;

    public androidTest() {
        this.driver = AppiumDriverFactory.getDriver();
    }

    public void scroll() {
        //driver.findElementByAndroidUIAutomator("new UiScrollable(new    UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"WebView\").instance(0))").click(); //scroll down to the element and click
        //driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().description(\"Create Order\").instance(0))")).click();
        try {
            driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"createOrder\").instance(0))")).click();
        } catch (Exception ex) {
            System.out.println("Scrolled");
        }

        //TouchAction tA = new TouchAction<>((PerformsTouchActions) driver);
        //tA.press(point(5, 10)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(point(1, 2)).release().perform();
    }
}
