package org.fasttrackit.steps.genericSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.fasttrackit.utils.TestUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GenericSteps extends TestBase {

    @Given("^I open \"([^\"]*)\"$")
    public void openUrl(String url) {
        driver.get(url);
    }

    @And("^I wait (\\d+) second(?:s*)$")
    public void waitSeconds(int seconds) {
        TestUtils.sleep(seconds);
    }

    @Then("^I am redirected to \"([^\"]*)\"$")
    public void I_am_redirected_to(String redirectionUrl) {
        String currentUrl = driver.getCurrentUrl();
        assertThat("Redirection failed.", currentUrl, is(redirectionUrl));
    }
}
