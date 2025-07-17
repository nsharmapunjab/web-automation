package com.nba.automation.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import com.nba.automation.utils.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

/**
 * TestNG listener for Allure integration
 * Handles screenshot capture and test result reporting
 */
public class AllureTestListener implements ITestListener {

    /**
     * Called when test fails
     * Captures screenshot and adds to Allure report
     */
    @Override
    public void onTestFailure(ITestResult result) {
        // Get the test method name
        String testName = result.getMethod().getMethodName();

        // Capture screenshot if driver is available
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                // Take screenshot
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                // Add screenshot to Allure report
                Allure.addAttachment("Screenshot - " + testName,
                        new ByteArrayInputStream(screenshot));

                // Add failure details
                Allure.addAttachment("Failure Details",
                        "Test: " + testName + "\nError: " + result.getThrowable().getMessage());

            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }

        // Log failure
        System.err.println("✗ Test failed: " + testName);
        if (result.getThrowable() != null) {
            System.err.println("Error: " + result.getThrowable().getMessage());
        }
    }

    /**
     * Called when test starts
     */
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("▶ Starting test: " + testName);
    }

    /**
     * Called when test succeeds
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("✓ Test passed: " + testName);
    }

    /**
     * Called when test is skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("⏭ Test skipped: " + testName);

        if (result.getThrowable() != null) {
            Allure.addAttachment("Skip Reason", result.getThrowable().getMessage());
        }
    }
}