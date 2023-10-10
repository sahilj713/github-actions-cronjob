package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

import java.io.File;
import java.io.IOException;


public class AndroidUtils {

    public final AppiumDriver driver;


    public AndroidUtils(AppiumDriver driver) {
        this.driver = driver;
    }


    public void waitFor(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Boolean objectExists(By by) {
        try {
            if (driver.findElements(by).size() == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean enterValueInTextBox(String text, By by) {
        boolean flag = false;
        try {
            if (driver.findElement(by).isDisplayed()) {
                driver.findElement(by).click();
                driver.findElement(by).clear();
                driver.findElement(by).sendKeys(text);
                //driver.hideKeyboard();
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public String getElementText(By by) {
        if (driver.findElement(by).isDisplayed()) {
            return driver.findElement(by).getText();
        } else {
            throw new NoSuchElementException("Element Not Found");
        }
    }
}
