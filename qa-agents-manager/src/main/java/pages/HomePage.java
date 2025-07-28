package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class HomePage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    // Locators
    private final By mainButton = By.xpath("//button[contains(text(), 'Click') or @type='button']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    @Step("Navigate to Home Page")
    public void navigateToHomePage(String url) {
        driver.get(url);
        waitUtils.waitForPageLoad();
    }

    @Step("Click main button on homepage")
    public void clickMainButton() {
        WebElement button = waitUtils.waitForElementClickable(mainButton);
        button.click();
    }

    @Step("Verify main button is displayed")
    public boolean isMainButtonDisplayed() {
        return waitUtils.waitForElementToBeDisplayed(mainButton);
    }
}
