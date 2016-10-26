package org.fasttrackit.steps.genericSteps;

import org.fasttrackit.utils.config.InitDriver;
import org.fasttrackit.utils.config.WebDriverConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;

public class TestBase {

    protected static WebDriver driver = WebDriverConfig.getDriver();

    static {
        if (driver == null) {
            try {
                InitDriver.initialize();
                driver = WebDriverConfig.getDriver();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void mouseOver(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).perform();
    }
}
