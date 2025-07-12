package com.nba.automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import com.nba.automation.config.ConfigManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base page class containing common functionality
 * All page objects should extend this class
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected ConfigManager config;

    /**
     * Constructor to initialize page elements
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.config = ConfigManager.getInstance();
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigate to specific URL
     * @param url Target URL
     */
    protected void navigateTo(String url) {
        driver.get(url);
    }

    protected void navigateToLeaderPage(String url) {
        driver.get(url);
        try {
            try {
                explicit_wait(driver.findElement(By.xpath("//*[contains(@id,'accept')]"))).click();
            } catch (NoSuchElementException e) {
                // Element not found within 3 seconds — safely ignore
                System.out.println("Element not found or not clickable: " + By.xpath("//*[contains(@id,'accept')]"));
            }
        } catch (Exception e) {
            System.out.println("Element not found or not clickable: " + By.xpath("//*[contains(@id,'accept')]"));
        }
    }

    public void clickIfPresent(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.click();
            System.out.println("Element clicked: " + locator);
        } catch (TimeoutException | NoSuchElementException e) {
            // Element not found within 3 seconds — safely ignore
            System.out.println("Element not found or not clickable: " + locator);
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
        }
    }

    private WebElement explicit_wait(WebElement elem) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.visibilityOf(elem));
    }
}