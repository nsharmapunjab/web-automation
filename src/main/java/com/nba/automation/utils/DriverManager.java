package com.nba.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.nba.automation.config.ConfigManager;

import java.time.Duration;

/**
 * WebDriver management utility class
 * Handles driver initialization, configuration, and cleanup
 */
public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ConfigManager config = ConfigManager.getInstance();

    /**
     * Initialize WebDriver based on configuration
     * Sets up browser-specific options and timeouts
     */
    public static void initializeDriver() {
        String browserName = config.getBrowser().toLowerCase();
        boolean headless = config.isHeadless();

        switch (browserName) {
            case "chrome":
                // Setup Chrome WebDriver using WebDriverManager
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                // Add Chrome-specific options for stability
                chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--start-maximized");

                if (headless) {
                    chromeOptions.addArguments("--headless");
                }

                chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER); // or NONE

                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                // Setup Firefox WebDriver using WebDriverManager
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }

                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            default:
                throw new IllegalArgumentException("Browser '" + browserName + "' is not supported");
        }

        // Configure timeouts
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        getDriver().manage().window().maximize();
    }

    /**
     * Get current WebDriver instance
     * @return WebDriver instance for current thread
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Close current WebDriver instance and clean up
     */
    public static void closeDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }
}