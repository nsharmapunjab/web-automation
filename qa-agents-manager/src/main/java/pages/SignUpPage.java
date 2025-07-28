package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class SignUpPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    private final By signUpLink = By.xpath("//a[contains(text(), 'Sign Up')]");

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    @Step("Verify Sign Up button is visible")
    public boolean isSignUpButtonVisible() {
        // Try multiple locators to find the Sign Up button

        try {
            if (waitUtils.waitForElementToBeDisplayed(signUpLink)) {
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
}
