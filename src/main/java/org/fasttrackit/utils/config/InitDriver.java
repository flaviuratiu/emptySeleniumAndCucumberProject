package org.fasttrackit.utils.config;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class InitDriver {
    private static final Logger LOGGER = Logger.getLogger(InitDriver.class);
    private static WebDriver driver;

    public static void initialize() throws IOException {
        LOGGER.info("===============================================================");
        LOGGER.info("|          Environment : " + EnvConfig.getTestEnvironment());
        LOGGER.info("|          Operating System : " + EnvConfig.getOperatingSystem());
        LOGGER.info("|          Browser: " + EnvConfig.getBrowser());
        LOGGER.info("|          Browser config file: " + EnvConfig.getBrowserConfigFile());
        LOGGER.info("===============================================================\n");
        if (driver == null) {
            driver = WebDriverConfig.getWebDriver(EnvConfig.getBrowserConfigFile());
            try {
                FileUtils.forceMkdir(new File(WebDriverConfig.getDownloadPath()));
            }
            catch (IOException e){
                LOGGER.error(e);
            }
        }
    }
}
