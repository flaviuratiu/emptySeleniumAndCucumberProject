package org.fasttrackit.utils.config;

import com.opera.core.systems.OperaDesktopDriver;
import org.fasttrackit.utils.TestUtils;
import org.fasttrackit.utils.config.browsers.*;
import org.fasttrackit.utils.config.properties.PropertiesReader;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class WebDriverConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverConfig.class);
    private static WebDriver driver;
    private static boolean isIE;
    private static boolean isOpera;
    private static boolean isSafari;
    private static boolean isChrome;
    private static boolean isFireFox;
    private static boolean isSilentDownload;
    private static String downloadsPath;

    public WebDriverConfig() {
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static boolean isIE() {
        return isIE;
    }

    public static boolean isOpera() {
        return isOpera;
    }

    public static boolean isSafari() {
        return isSafari;
    }

    public static boolean isChrome() {
        return isChrome;
    }

    public static boolean isFireFox() {
        return isFireFox;
    }

    public static void init(WebDriver driver) {
        if (driver != null) {
            LOGGER.info("===============================================================");
            LOGGER.info("|          Open Selenium Web Driver ");
            LOGGER.info("===============================================================\n");
            if (driver instanceof InternetExplorerDriver) {
                isIE = true;
            } else if (driver instanceof ChromeDriver) {
                isChrome = true;
            } else if (driver instanceof FirefoxDriver) {
                isFireFox = true;
            } else if (driver instanceof SafariDriver) {
                isSafari = true;
            } else if (driver instanceof OperaDesktopDriver) {
                isOpera = true;
            }

            driver.manage().window().maximize();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    WebDriverConfig.initSeleniumEnd();
                }
            });
        }

    }

    private static void initSeleniumEnd() {
        LOGGER.info("===============================================================");
        LOGGER.info("|          Stopping driver (closing browser)                   |");
        LOGGER.info("===============================================================");
        driver.quit();
        LOGGER.debug("===============================================================");
        LOGGER.debug("|         Driver stopped (browser closed)                     |");
        LOGGER.debug("===============================================================\n");
    }

    public static boolean isSilentDownload() {
        return isSilentDownload;
    }

    private static void setSilentDownload(boolean silentDownload) {
        isSilentDownload = silentDownload;
    }

    public static String getDownloadPath() {
        return downloadsPath;
    }

    public static void setDownloadPath(String downloadPath) {
        downloadsPath = downloadPath;
    }

    public static WebDriver getWebDriver(String browserProperties) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(browserProperties);
        LOGGER.debug("File: {} " + (resource != null ? "exists" : "does not exist"), browserProperties);
        if (resource != null) {
            Browser browser = findBrowser(resource.openStream());
            return getDriver(browser, resource.openStream());
        } else {
            return null;
        }
    }

    public static WebDriver getWebDriver(Browser browser) throws IOException {
        return getDriver(browser, (InputStream) null);
    }

    private static WebDriver getDriver(Browser browser, InputStream inputStream) throws IOException {
        Object properties = null;
        if (browser == Browser.FIREFOX) {
            properties = new FirefoxConfigReader();
        } else if (browser == Browser.IE) {
            properties = new IExplorerConfigReader();
        } else if (browser == Browser.CHROME) {
            properties = new ChromeConfigReader();
        } else if (browser == Browser.HTMLUNIT) {
            properties = new HtmlUnitConfigReader();
        } else {
            LOGGER.error("Browser not supported {}", browser);
            driver = null;
        }

        if (properties != null) {
            if (inputStream != null) {
                ((AbstractBrowserConfigReader) properties).load(inputStream);
            }

            driver = ((AbstractBrowserConfigReader) properties).createDriver();
            setDownloadPath(((AbstractBrowserConfigReader) properties).getDownloadPath());
            setSilentDownload(((AbstractBrowserConfigReader) properties).isSilentDownload());
        }

        init(driver);
        return driver;
    }

    public static Browser getBrowser(String browserKey) {
        browserKey = browserKey.toUpperCase();
        Browser browser = null;

        try {
            browser = Browser.valueOf(browserKey);
        } catch (IllegalArgumentException var3) {
            LOGGER.error("BROWSER not supported : {}. Supported browsers: {}", browserKey, Arrays.asList(Browser.values()));
        }

        return browser;
    }

    private static Browser findBrowser(InputStream inputStream) {
        PropertiesReader properties = new PropertiesReader((String) null, inputStream);
        String browserKey = properties.getProperty("browser");
        LOGGER.info("Browser is: {}", browserKey);
        return getBrowser(browserKey);
    }

    public static String switchToLastTab() {
        int totalTabs = driver.getWindowHandles().size();
        return switchToTab(totalTabs - 1);
    }

    public static String switchToFirstTab() {
        return switchToTab(0);
    }

    public static String switchToTab(int index) {
        String oldTabName = null;

        try {
            TestUtils.sleep(100L);

            try {
                oldTabName = driver.getWindowHandle();
                LOGGER.debug("Preview tab id: {}", oldTabName);
                LOGGER.info("Preview tab title : {}", driver.getTitle());
            } catch (NoSuchWindowException var4) {
                LOGGER.info("Preview tab already closed");
            }

            ArrayList e = new ArrayList(driver.getWindowHandles());
            String tabID = (String) e.get(index);
            LOGGER.debug("Switch to tab id: {}", tabID);
            driver.switchTo().window(tabID);
            LOGGER.info("Current tab title : {}", driver.getTitle());
        } catch (NoSuchWindowException var5) {
            LOGGER.error("NoSuchWindowException", var5);
        }

        return oldTabName;
    }

    public static boolean waitForNewTab(int tabCount, long millis) {
        boolean hasExpectedTabs;
        for (hasExpectedTabs = false; !hasExpectedTabs && millis > 0L; millis -= 100L) {
            if (driver.getWindowHandles().size() >= tabCount) {
                hasExpectedTabs = true;
            } else {
                LOGGER.info("Waiting {} ms for new tab...", Long.valueOf(millis));
                TestUtils.sleep(100L);
            }
        }

        return hasExpectedTabs;
    }
}
