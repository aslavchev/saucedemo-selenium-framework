package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Checkout Complete page - order confirmation.
 */
public class CheckoutCompletePage extends BasePage {

    private static final By COMPLETE_HEADER = By.cssSelector("[data-test='complete-header']");
    private static final By COMPLETE_TEXT = By.cssSelector("[data-test='complete-text']");
    private static final By BACK_HOME_BUTTON = By.cssSelector("[data-test='back-to-products']");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(COMPLETE_HEADER);
    }

    public String getHeader() {
        return getText(COMPLETE_HEADER);
    }

    public String getCompleteText() {
        return getText(COMPLETE_TEXT);
    }

    public void backToHome() {
        click(BACK_HOME_BUTTON);
    }
}
