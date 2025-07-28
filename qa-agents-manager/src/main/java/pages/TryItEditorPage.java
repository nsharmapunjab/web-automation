package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class TryItEditorPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    // Locators
    private final By iframe = By.id("iframeResult");
    private final By w3schoolsLink = By.xpath("//a[contains(text(), 'Visit W3Schools!') or @href='https://www.w3schools.com']");

    public TryItEditorPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    @Step("Navigate to W3Schools Try It Editor page")
    public void navigateToTryItEditor(String url) {
        driver.get(url);
        waitUtils.waitForPageLoad();
    }

    @Step("Switch to iframe containing the link")
    public void switchToIframe() {
        waitUtils.waitForFrameAndSwitch(iframe);
    }

    @Step("Click Visit W3Schools link")
    public void clickW3SchoolsLink() {
        WebElement link = waitUtils.waitForElementClickable(w3schoolsLink);
        link.click();
    }

    @Step("Switch to new window/tab")
    public void switchToNewWindow() {
        // Get all window handles
        String originalWindow = driver.getWindowHandle();
        
        // Wait for new window to open
        waitUtils.waitForPageLoad();
        
        // Switch to the new window
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        waitUtils.waitForPageLoad();
    }

    @Step("Verify W3Schools link is present in iframe")
    public boolean isW3SchoolsLinkPresent() {
        try {
            switchToIframe();
            return waitUtils.waitForElementToBeDisplayed(w3schoolsLink);
        } catch (Exception e) {
            return false;
        } finally {
            driver.switchTo().defaultContent();
        }
    }
}
