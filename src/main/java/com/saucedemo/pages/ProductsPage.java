package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Products page - displays inventory after login.
 */
public class ProductsPage extends BasePage {

    private static final By PAGE_TITLE = By.cssSelector("[data-test='title']");
    private static final By INVENTORY_LIST = By.cssSelector("[data-test='inventory-list']");
    private static final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(INVENTORY_LIST);
    }

    public String getPageTitle() {
        return getText(PAGE_TITLE);
    }

    public void addProductToCart(String productId) {
        By addButton = By.cssSelector("[data-test='add-to-cart-" + productId + "']");
        click(addButton);
    }

    public int getCartItemCount() {
        if (!isDisplayed(CART_BADGE)) {
            return 0;
        }
        return Integer.parseInt(getText(CART_BADGE));
    }
}
