package com.nba.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.nba.automation.config.ConfigManager;

import java.time.Duration;
import java.util.List;

/**
 * WebDriver wait utility class
 * Provides common wait operations for element interactions
 */
public class WaitHelper {
    private static ConfigManager config = ConfigManager.getInstance();

    /**
     * Wait for element to be visible and return it
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return WebElement when visible
     */
    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for elements to be present and return them
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return List of WebElements when present
     */
    public static List<WebElement> waitForElementsToBePresent(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
}