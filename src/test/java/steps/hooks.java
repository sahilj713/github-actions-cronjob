package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import utilities.AppiumDriverFactory;
import utilities.ConfigReader;

import java.net.MalformedURLException;

public class hooks {

    public static String executionType = ConfigReader.getConfigPropertyVal("executionType");
    WebServiceFactory webSF = new WebServiceFactory();

    @Before()
    public void launchBrowser() throws MalformedURLException {
        if (executionType.equals("API")) {
            ConfigReader.setProperties("config", "token", webSF.getToken());
        } else {
            AppiumDriverFactory.launchApp();
        }
    }

    @After()
    public void quiteBrowser(Scenario scenario) {
        if (executionType.equals("API")) {
            System.out.println("API execution completed");
        } else {
            AppiumDriverFactory.closeDriver(scenario);
        }
    }
}
