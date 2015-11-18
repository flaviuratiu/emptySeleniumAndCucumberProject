package org.fasttrackit.utils.config.browsers;

public enum Browser {
    IE("ie"),
    FIREFOX("firefox"),
    CHROME("chrome"),
    HTMLUNIT("htmlunit");

    private String driverKey;

    private Browser(String driverKey) {
        this.driverKey = driverKey;
    }

    public String getDriverKey() {
        return this.driverKey;
    }
}
