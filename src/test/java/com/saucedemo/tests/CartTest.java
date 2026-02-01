package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.testdata.Products;
import com.saucedemo.testdata.TestUsers;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Cart page functionality tests.
 */
public class CartTest extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;

    @BeforeMethod
    public void loginAndSetup() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestUsers.STANDARD.username(), TestUsers.STANDARD.password());
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
    }

    @Description("Verify cart displays item after adding from products page")
    @Test(groups = {"smoke"})
    public void cartDisplaysAddedItem() {
        // Arrange
        productsPage.addProductToCart(Products.BACKPACK.id());

        // Act
        productsPage.goToCart();

        // Assert
        assertTrue(cartPage.isLoaded(), "Cart page should be loaded");
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should have 1 item");
    }

    @Description("Verify removing an item empties the cart")
    @Test(groups = {"smoke"})
    public void cartEmptiesAfterRemovingItem() {
        // Arrange
        productsPage.addProductToCart(Products.BACKPACK.id());
        productsPage.goToCart();

        // Act
        cartPage.removeProduct(Products.BACKPACK.id());

        // Assert
        assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty after removal");
    }

    @Description("Verify continue shopping returns user to products page")
    @Test(groups = {"regression"})
    public void continueShoppingReturnsToProducts() {
        // Arrange
        productsPage.goToCart();

        // Act
        cartPage.continueShopping();

        // Assert
        assertTrue(productsPage.isLoaded(), "Should return to products page");
    }
}
