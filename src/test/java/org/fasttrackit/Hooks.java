package org.fasttrackit;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.log4j.Logger;
import org.fasttrackit.utils.config.InitDriver;

import java.io.IOException;

public class Hooks {
    private static final Logger LOGGER = Logger.getLogger(Hooks.class);

    @Before
    public void beforeScenario(Scenario scenario)throws IOException {
        LOGGER.debug("***** START of Scenario: " + scenario.getName() + " *****");
        InitDriver.initialize();
    }

    @After
    public void tearDown(Scenario scenario) {
        LOGGER.debug("***** END of Scenario: " + scenario.getName() + " *****");
    }
}
