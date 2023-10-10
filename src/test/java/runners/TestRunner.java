package runners;

import java.util.Arrays;
import java.util.Objects;

import io.cucumber.testng.*;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;
import utilities.SendEmail;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"steps"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        tags = "@API")

public class TestRunner extends AbstractTestNGCucumberTests {

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass(ITestContext context) {
        XmlTest currentXmlTest = context.getCurrentXmlTest();
        Objects.requireNonNull(currentXmlTest);
        CucumberPropertiesProvider properties = currentXmlTest::getParameter;
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass(), properties);
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        this.testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return this.testNGCucumberRunner == null ? new Object[0][0] : this.testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass(ITestContext context) throws Exception {
        int[] testResult = new int[3];
        testResult[0] = context.getPassedTests().size();
        testResult[1] = context.getFailedTests().size();
        testResult[2] = context.getSkippedTests().size();
        if (this.testNGCucumberRunner != null) {
            this.testNGCucumberRunner.finish();
        }
        try {
            SendEmail.sendEmail(testResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
