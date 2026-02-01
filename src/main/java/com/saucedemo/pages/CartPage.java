package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Cart page - displays items added to shopping cart.
 */
public class CartPage extends BasePage {

    private static final By CART_LIST = By.cssSelector("[data-test='cart-list']");
    private static final By CART_ITEM = By.cssSelector("[data-test='inventory-item']");
    private static final By CONTINUE_SHOPPING = By.cssSelector("[data-test='continue-shopping']");
    private static final By CHECKOUT_BUTTON = By.cssSelector("[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(CART_LIST);
    }

    public int getCartItemCount() {
        return getElementCount(CART_ITEM);
    }

    public void removeProduct(String productId) {
        By removeButton = By.cssSelector("[data-test='remove-" + productId + "']");
        click(removeButton);
    }

    public void continueShopping() {
        click(CONTINUE_SHOPPING);
    }

    public void proceedToCheckout() {
        click(CHECKOUT_BUTTON);
    }
}
