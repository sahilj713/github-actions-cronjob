package steps;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import pages.Order;
import pages.LoginLogout;
import pages.OrderManagement;
import pages.androidTest;
import utilities.AppiumDriverFactory;

import java.time.Duration;

public class CreateOrder {

    private final AppiumDriver driver;
    public WebDriverWait wait;

    public final LoginLogout loginLogout;
    private final OrderManagement oManage;

    public final androidTest aTest;


    public CreateOrder() {
        driver = AppiumDriverFactory.getDriver();
        loginLogout = new LoginLogout();
        oManage = new OrderManagement();
        aTest = new androidTest();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    @Given("User navigates to app home page")
    public void userNavigatesToAppHomePage() throws InterruptedException {
        System.out.println("------Inpatient test execution started!!!------");
        Thread.sleep(5000);
        //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.view.View[@content-desc=\"username_textfield\"]/android.widget.ImageView"))));
    }

    @When("User login with {string} and {string} creds")
    public void userLoginWithAndCreds(String username, String password) {
        loginLogout.login(username, password);
    }

    @When("User selects {string} facility")
    public void userSelectsFacility(String facility) throws InterruptedException {
        Thread.sleep(3000);
        oManage.facilitySelection(facility);
    }

    @And("User clicks on {string} button")
    public void userClicksOnButton(String button) throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"" + button + "\"]")).click();
    }

    @And("User clicks on {string} icon")
    public void userClicksOnIcon(String icon) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"Create Order\"]"))));
        driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"Create Order\"]")).click();
    }

    @And("User enters details for create order")
    public void userEntersDetailsForCreateOrder() throws InterruptedException {
        //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.EditText[1]"))));
        Thread.sleep(3000);

        driver.findElement(By.xpath("//android.view.View[@content-desc=\"firstName_textfield\"]/android.widget.EditText")).click();
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"firstName_textfield\"]/android.widget.EditText")).sendKeys("QA1");
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"lastName_textfield\"]/android.widget.EditText")).click();
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"lastName_textfield\"]/android.widget.EditText")).sendKeys("Test1");
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"dateOfBirth_textfield\"]/android.widget.EditText")).click();
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"dateOfBirth_textfield\"]/android.widget.EditText")).sendKeys("07/08/1982");
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"mobileNumber_textfield\"]/android.widget.EditText")).click();
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"mobileNumber_textfield\"]/android.widget.EditText")).sendKeys("1234567890");
    }

    @And("User selects {string} language")
    public void userSelectsLanguage(String language) throws InterruptedException {
        driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Select Language\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"" + language + "\"]")).click();
    }

    @Then("User should be on {string} page")
    public void userShouldBeOnPage(String page) throws InterruptedException {
        Thread.sleep(4000);
        oManage.pageRedirectionCheck(page);
    }

    @And("User attach file for {string}")
    public void userAttachFileAt(String attachmentField) throws InterruptedException {
        Thread.sleep(3000);
        //Need to add scrolling to handle Attach Prescription
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.view.View[@content-desc=\"" + attachmentField + "\"]"))));
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"" + attachmentField + "\"]")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.view.View[@content-desc=\"Choose from gallery\"]"))));
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"Choose from gallery\"]")).click();
        //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@content-desc,\"1 MB.jpg\")]"))));
        Thread.sleep(5000);

        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@content-desc,\"jpg\")]")).click();
        Thread.sleep(8000);
        aTest.scroll();
    }

    @And("User should see order created toast message")
    public void userShouldSeeToastMessage() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(new AppiumBy.ByAccessibilityId("Order created successfully"))));
    }
}
