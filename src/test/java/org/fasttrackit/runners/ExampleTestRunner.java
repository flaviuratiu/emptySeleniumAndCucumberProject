package org.fasttrackit.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        plugin =  {"rerun", "html:target/cucumber","json:target/jsonReports/ExampleTest.json"},
        glue = {"org.fasttrackit"},
        features = {
                "src/test/resources/features/example-test.feature"
        }
)

public class ExampleTestRunner {
}
