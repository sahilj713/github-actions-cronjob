package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.AppiumDriverFactory;

public class OrderManagement {

    AppiumDriver driver;

    @FindBy(xpath = "//android.view.View[contains(@content-desc,'Select a Facility')]")
    private WebElement selectFacilityPage;

    @FindBy(xpath = "//android.view.View[contains(@content-desc,'All Orders')]")
    private WebElement allOrdersPage;

    @FindBy(xpath = "(//android.view.View[@content-desc='Baptist Medical Center'])[1]")
    private WebElement bmcFacilitySelection;


    public OrderManagement() {
        driver = AppiumDriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void pageRedirectionCheck(String pageName) {
        if (pageName.contains("Select a Facility")) {
            selectFacilityPage.isDisplayed();
        } else if (pageName.equals("All Orders")) {
            allOrdersPage.isDisplayed();
        }
    }

    public void facilitySelection(String facility) {
        if (facility.equals("Baptist Medical Center")) {
            bmcFacilitySelection.click();
        } else {
            System.out.println("Facility to select is not Sterling");
        }
    }
}
