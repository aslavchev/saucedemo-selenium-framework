package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

/**
 * Checkout Step One - Your Information page.
 */
public class CheckoutInfoPage extends BasePage {

    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.cssSelector("[data-test='continue']");
    private static final By CANCEL_BUTTON = By.cssSelector("[data-test='cancel']");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public CheckoutInfoPage(WebDriver driver) {
        super(driver);
    }

    public void fillCustomerInfo(String firstName, String lastName, String postalCode) {
        type(FIRST_NAME, firstName);
        type(LAST_NAME, lastName);
        type(POSTAL_CODE, postalCode);
    }

    public void continueToOverview() {
        click(CONTINUE_BUTTON);
    }

    public void cancel() {
        click(CANCEL_BUTTON);
    }

    public boolean isErrorDisplayed() {
        try {
            waitForVisible(ERROR_MESSAGE);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }
}
