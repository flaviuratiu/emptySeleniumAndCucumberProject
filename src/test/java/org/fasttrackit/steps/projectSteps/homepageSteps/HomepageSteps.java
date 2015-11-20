package org.fasttrackit.steps.projectSteps.homepageSteps;

import cucumber.api.java.en.Given;
import org.fasttrackit.common.inputData.GenericData;
import org.fasttrackit.steps.genericSteps.TestBase;
import org.fasttrackit.views.ExamplePage;
import org.openqa.selenium.support.PageFactory;

public class HomepageSteps extends TestBase {

    ExamplePage examplePage = PageFactory.initElements(driver, ExamplePage.class);

    @Given("^I press the More information link$")
    public void pressMoreInformationLink() {
        examplePage.getMoreInformationLink().click();
    }

    @Given("^I open the homepage$")
    public void openHomepage() {
        driver.get(GenericData.HOMEPAGE_LINK);
    }
}
