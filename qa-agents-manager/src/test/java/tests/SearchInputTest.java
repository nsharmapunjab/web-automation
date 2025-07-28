package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TryItEditorPage;
import pages.W3HomePage;

public class SearchInputTest extends BaseTest {

    @Epic("User Interface Testing")
    @Feature("Navigation and Element Visibility")
    @Story("Validate Search Input Field Visibility After Link Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to validate that search input field is visible after navigating through iframe link to W3Schools")
    @Test(description = "Verify Search Input Field Visibility Test")
    public void validateSearchInputVisibility() {
        // Test data
        String tryItEditorUrl = "https://www.w3schools.com/html/tryit.asp?filename=tryhtml_links_target";

        // Initialize page objects
        TryItEditorPage tryItEditorPage = new TryItEditorPage(getDriver());
        W3HomePage w3HomePage = new W3HomePage(getDriver());

        // Test steps
        Allure.step("Step 1: Navigate to W3Schools Try It Editor page", () -> {
            tryItEditorPage.navigateToTryItEditor(tryItEditorUrl);
            Assert.assertTrue(tryItEditorPage.isW3SchoolsLinkPresent(), 
                "Visit W3Schools link should be present in the iframe");
        });

        Allure.step("Step 2: Switch to iframe and click Visit W3Schools link", () -> {
            tryItEditorPage.switchToIframe();
            tryItEditorPage.clickW3SchoolsLink();
        });

        Allure.step("Step 3: Switch to new window/tab", () -> {
            tryItEditorPage.switchToNewWindow();
        });

        Allure.step("Step 4: Verify search input field is visible", () -> {
            // Wait for the new page to load
            w3HomePage.waitForPageLoad();
            
            // Add some additional wait time
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            boolean isSearchInputVisible = w3HomePage.isSearchInputVisible();
            
            // Add additional context to the assertion
            Allure.addAttachment("Current URL", "text/plain", w3HomePage.getCurrentUrl());
            Allure.addAttachment("Current Title", "text/plain", w3HomePage.getCurrentTitle());
            
            Assert.assertTrue(isSearchInputVisible, 
                "Search input field should be visible on W3Schools homepage. Current URL: " + w3HomePage.getCurrentUrl());
        });
    }
}
