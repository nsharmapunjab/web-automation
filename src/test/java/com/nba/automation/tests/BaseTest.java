package com.nba.automation.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import com.nba.automation.utils.DriverManager;
import com.nba.automation.listeners.AllureTestListener;
import io.qameta.allure.Allure;

/**
 * Base test class containing common setup and teardown
 * All test classes should extend this class
 */
@Listeners(AllureTestListener.class)
public class BaseTest {

    /**
     * Setup method executed before each test
     * Initializes WebDriver and browser
     */
    @BeforeMethod
    public void setUp() {
        // Initialize WebDriver
        DriverManager.initializeDriver();

        // Add environment information to Allure report
        Allure.addAttachment("Test Environment", "NBA Stats Website Automation");
        Allure.addAttachment("Browser", "Chrome");
        Allure.addAttachment("Base URL", "https://stats.nba.com");
    }

    /**
     * Teardown method executed after each test
     * Closes WebDriver and cleans up resources
     */
    @AfterMethod
    public void tearDown() {
        // Close WebDriver
        DriverManager.closeDriver();
    }
}