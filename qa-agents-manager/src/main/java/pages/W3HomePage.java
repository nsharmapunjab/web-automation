package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class W3HomePage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    private final By searchInput = By.id("search2");

    public W3HomePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    @Step("Verify search input field is visible")
    public boolean isSearchInputVisible() {

        try {
            if (waitUtils.waitForElementToBeDisplayed(searchInput)) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception while waiting for element : " + e);
            return false;
        }

        return false;
    }

    @Step("Get current page URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Get current page title")
    public String getCurrentTitle() {
        return driver.getTitle();
    }

    @Step("Wait for page to load completely")
    public void waitForPageLoad() {
        waitUtils.waitForPageLoad();
    }
}
