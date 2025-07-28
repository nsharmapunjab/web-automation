package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SignUpPage;

public class SignUpButtonTest extends BaseTest {

    @Epic("User Interface Testing")
    @Feature("Navigation and Button Visibility")
    @Story("Validate Sign-Up Button Visibility After Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to validate that Sign-Up button is visible after clicking main button on homepage")
    @Test(description = "Verify Sign Up Button Visibility Test")
    public void validateSignUpButtonVisibility() {
        // Test data
        String homePageUrl = "https://v0-button-to-open-v0-home-page-h5dizpkwp.vercel.app/";

        // Initialize page objects
        HomePage homePage = new HomePage(getDriver());
        SignUpPage signUpPage = new SignUpPage(getDriver());

        // Test steps
        Allure.step("Step 1: Navigate to homepage", () -> {
            homePage.navigateToHomePage(homePageUrl);
            Assert.assertTrue(homePage.isMainButtonDisplayed(), "Main button should be displayed on homepage");
        });

        Allure.step("Step 2: Click the main button and switch to new tab", () -> {
            homePage.clickMainButton();
            // Switch to new tab
            String currentWindowHandle = getDriver().getWindowHandle();
            for (String windowHandle : getDriver().getWindowHandles()) {
                if (!windowHandle.equals(currentWindowHandle)) {
                    getDriver().switchTo().window(windowHandle);
                    break;
                }
            }
        });

        Allure.step("Step 3: Verify Sign Up button is visible", () -> {
            // Add some wait time for page transition
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            boolean isSignUpVisible = signUpPage.isSignUpButtonVisible();
            
            // Add additional context to the assertion
            Allure.addAttachment("Current URL", "text/plain", signUpPage.getCurrentUrl());
            Allure.addAttachment("Current Title", "text/plain", signUpPage.getCurrentTitle());
            
            Assert.assertTrue(isSignUpVisible, 
                "Sign Up button should be visible on the new page. Current URL: " + signUpPage.getCurrentUrl());
        });
    }
}
