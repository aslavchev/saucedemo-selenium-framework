package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Products page - displays inventory after login.
 */
public class ProductsPage extends BasePage {

    private static final By PAGE_TITLE = By.cssSelector("[data-test='title']");
    private static final By INVENTORY_LIST = By.cssSelector("[data-test='inventory-list']");
    private static final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");
    private static final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");
    private static final By SORT_DROPDOWN = By.cssSelector("[data-test='product-sort-container']");
    private static final By PRODUCT_ITEM_NAME = By.cssSelector("[data-test='inventory-item-name']");
    private static final By PRODUCT_IMAGE = By.cssSelector("[data-test='inventory-item'] img");

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

    public void removeProductFromCart(String productId) {
        By removeButton = By.cssSelector("[data-test='remove-" + productId + "']");
        click(removeButton);
    }

    public int getCartItemCount() {
        if (!isDisplayed(CART_BADGE)) {
            return 0;
        }
        return Integer.parseInt(getText(CART_BADGE));
    }

    public void goToCart() {
        click(CART_LINK);
    }

    public void sortByNameAscending() {
        Select sortSelect = new Select(waitForVisible(SORT_DROPDOWN));
        sortSelect.selectByValue("az");
    }

    public List<String> getProductNames() {
        List<WebElement> elements = findElements(PRODUCT_ITEM_NAME);
        List<String> names = new ArrayList<>();
        for (WebElement element : elements) {
            names.add(element.getText());
        }
        return names;
    }

    public List<String> getProductImageSources() {
        List<WebElement> images = findElements(PRODUCT_IMAGE);
        List<String> sources = new ArrayList<>();
        for (WebElement image : images) {
            sources.add(image.getAttribute("src"));
        }
        return sources;
    }
}
