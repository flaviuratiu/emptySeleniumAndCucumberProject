package org.fasttrackit.steps.genericSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.fasttrackit.utils.TestUtils;
import org.fasttrackit.utils.config.WebDriverConfig;

public class GenericSteps {

    @Given("^I open \"([^\"]*)\"$")
    public void openUrl(String url) {
        WebDriverConfig.getDriver().get(url);
    }

    @And("^I wait (\\d+) second(?:s*)$")
    public void waitSeconds(int seconds) {
        TestUtils.sleep(seconds);
    }
}
