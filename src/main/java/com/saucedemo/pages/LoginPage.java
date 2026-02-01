package com.saucedemo.pages;

import com.saucedemo.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Login page - entry point to SauceDemo application.
 */
public class LoginPage extends BasePage {

    // Using data-test attributes (testing-specific, stable)
    private static final By USERNAME_FIELD = By.cssSelector("[data-test='username']");
    private static final By PASSWORD_FIELD = By.cssSelector("[data-test='password']");
    private static final By LOGIN_BUTTON = By.cssSelector("[data-test='login-button']");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo(TestConfig.getBaseUrl());
    }

    public void login(String username, String password) {
        type(USERNAME_FIELD, username);
        type(PASSWORD_FIELD, password);
        click(LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(ERROR_MESSAGE);
    }
}
