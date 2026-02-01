package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Checkout Step Two - Overview page.
 */
public class CheckoutOverviewPage extends BasePage {

    private static final By FINISH_BUTTON = By.cssSelector("[data-test='finish']");
    private static final By CANCEL_BUTTON = By.cssSelector("[data-test='cancel']");
    private static final By ITEM_TOTAL = By.cssSelector("[data-test='subtotal-label']");
    private static final By TAX = By.cssSelector("[data-test='tax-label']");
    private static final By TOTAL = By.cssSelector("[data-test='total-label']");
    private static final By CART_ITEM = By.cssSelector("[data-test='inventory-item']");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(FINISH_BUTTON);
    }

    public void finishCheckout() {
        click(FINISH_BUTTON);
    }

    public void cancel() {
        click(CANCEL_BUTTON);
    }

    public String getItemTotal() {
        return getText(ITEM_TOTAL);
    }

    public String getTax() {
        return getText(TAX);
    }

    public String getTotal() {
        return getText(TOTAL);
    }

    public int getItemCount() {
        return getElementCount(CART_ITEM);
    }
}
