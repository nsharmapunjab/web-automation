package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import java.time.Duration;

public class WaitUtils {
    private WebDriverWait wait;
    private WebDriver driver;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForElementToBeDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            System.out.println("Exception : " + e);
            return false;
        }
    }

    public void waitForPageLoad() {
        wait.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
            .executeScript("return document.readyState").equals("complete"));
    }

    public void waitForFrameAndSwitch(By frameLocator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public void waitForFrameAndSwitch(String frameNameOrId) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
    }
}
