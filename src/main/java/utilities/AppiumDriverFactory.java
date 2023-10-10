package utilities;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
//import io.appium.java_client.android.AndroidElement;

public class AppiumDriverFactory {

    //private static final AppiumDriverFactory instanceOfAppiumDriverFactory = null;
    //public static AppiumDriver driver;
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    private static final String appPackage = ConfigReader.getConfigPropertyVal("appPackage");
    private static final String appActivity = ConfigReader.getConfigPropertyVal("appActivity");
    private static final String deviceName = ConfigReader.getConfigPropertyVal("deviceName");


    public static void launchApp() throws MalformedURLException {
        AppiumDriver driver = androidDriver();
        setDriver(driver);
    }

    // Declaring constructor as private to restrict object creation outside of class
    public static AppiumDriver androidDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "ANDROID");
        capabilities.setCapability("device", deviceName);
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);
        capabilities.setCapability("automationName", "UIAutomator2");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("unicodeKeyboard", true);
        //AppiumDriver driver;

        return new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
    }

    public static void setDriver(AppiumDriver driver) {
        AppiumDriverFactory.driver.set(driver);
    }

    public static synchronized void closeDriver(Scenario scenario) {
        takeScreenshot(scenario);
        getDriver().quit();
    }

    public static void takeScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] sourcePath = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(sourcePath, "image/png", screenshotName);
        }
    }

    // To get driver
    public static AppiumDriver getDriver() {
        return driver.get();
    }
}
