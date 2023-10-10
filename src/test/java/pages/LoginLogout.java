package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.AppiumDriverFactory;

import java.time.Duration;

public class LoginLogout {

    AppiumDriver driver;

    //@AndroidFindBy(xpath = "//android.view.View[@content-desc='username_textfield']/android.widget.ImageView")
    @AndroidFindBy(xpath = "//android.view.View[@content-desc='username_textfield']/android.widget.EditText")
    //@iOSXCUITFindBy(xpath = " ")
    private WebElement user;

    @FindBy(xpath = "//android.view.View[@content-desc='password_textfield']/android.widget.EditText")
    private WebElement pass;

    @FindBy(xpath = "//android.widget.Button[@content-desc='Login']")
    private WebElement login;

    public LoginLogout() {
        driver = AppiumDriverFactory.getDriver();
        //PageFactory.initElements(driver, this);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(60)), this);
    }

    public void login(String username, String password) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(username + "----" + password);
        user.click();
        user.sendKeys(username);
        pass.click();
        pass.sendKeys(password);
        login.click();
    }
}
