package runners;

//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;
import org.testng.annotations.Test;

@Test
//@RunWith(Cucumber.class)
@CucumberOptions(

        features = "src/test/resources/features",
        glue = "org.vodafoneCart.Stepdefns",
        plugin = {
                "pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)

public class TestRunner  extends AbstractTestNGCucumberTests{
}
//extends AbstractTestNGCucumberTests