package runners;

//import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;

//@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"}, // Path to the feature file
        glue = {"org.vodafoneCart.Stepdefns"}, // Package containing step definitions
        plugin = {"pretty",
                "html:target/cucumber-reports.html"}, // Generates reports
        monochrome = true // Makes console output readable
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
//"html:target/cucumber-reports.html"
/*
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.vodafoneCart.Stepdefns",
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
       monochrome = true*/