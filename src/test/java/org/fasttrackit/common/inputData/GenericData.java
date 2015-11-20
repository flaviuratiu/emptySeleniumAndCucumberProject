package org.fasttrackit.common.inputData;

import org.apache.log4j.Logger;
import org.fasttrackit.utils.config.EnvConfig;
import org.fasttrackit.utils.config.properties.PropertiesReader;

public class GenericData extends PropertiesReader {
    private static final Logger LOGGER = Logger.getLogger(GenericData.class);

    private GenericData() {
        super(EnvConfig.getTestConfigPath());
    }

    static GenericData genericData = new GenericData();

    public static final String HOMEPAGE_LINK = genericData.getProperty("frontend.url");
}
