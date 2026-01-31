package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Base class for all Page Objects.
 * Provides common actions: click, type, getText, waits.
 */
public class BasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    protected final Logger logger;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    // ==================== Core Actions ====================

    @Step("Click element: {locator}")
    protected void click(By locator) {
        logger.debug("Clicking element: {}", locator);
        waitForClickable(locator).click();
    }

    @Step("Type '{text}' into element: {locator}")
    protected void type(By locator, String text) {
        logger.debug("Typing '{}' into element: {}", text, locator);
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    @Step("Get text from element: {locator}")
    protected String getText(By locator) {
        String text = waitForVisible(locator).getText();
        logger.debug("Got text '{}' from element: {}", text, locator);
        return text;
    }

    // ==================== State Checks ====================

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    // ==================== Waits ====================

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
