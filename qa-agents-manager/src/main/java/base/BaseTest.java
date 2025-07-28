package base;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.WaitUtils;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WaitUtils waitUtils;

    @BeforeMethod
    public void setUp() {
        // Set up Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        
        // Initialize WebDriver
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Initialize WebDriverWait and utilities
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        waitUtils = new WaitUtils(driver);
        
        Allure.addAttachment("Browser Setup", "text/plain", "Chrome browser initialized successfully");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            // Take screenshot for Allure report
            try {
                byte[] screenshot = ((org.openqa.selenium.TakesScreenshot) driver)
                    .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                Allure.addAttachment("Final Screenshot", "image/png", 
                    new ByteArrayInputStream(screenshot), "png");
            } catch (Exception e) {
                System.out.println("Could not take screenshot: " + e.getMessage());
            }
            
            driver.quit();
            Allure.addAttachment("Browser Teardown", "text/plain", "Chrome browser closed successfully");
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }

}
