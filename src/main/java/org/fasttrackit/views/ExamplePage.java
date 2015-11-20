package org.fasttrackit.views;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExamplePage {

    @FindBy(linkText = "More information...")
    private WebElement moreInformationLink;

    public WebElement getMoreInformationLink() {
        return moreInformationLink;
    }
}
