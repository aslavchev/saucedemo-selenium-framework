package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.testdata.Products;
import com.saucedemo.testdata.TestUsers;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Products page functionality tests.
 */
public class ProductsTest extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod
    public void loginFirst() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestUsers.STANDARD.username(), TestUsers.STANDARD.password());
        productsPage = new ProductsPage(driver);
    }

    @Description("Verify products page loads correctly after login")
    @Test(groups = {"smoke"})
    public void productsPageLoadsAfterLogin() {
        // Arrange - done in @BeforeMethod

        // Act - page already loaded after login

        // Assert
        assertTrue(productsPage.isLoaded(), "Products page should be loaded");
        assertEquals(productsPage.getPageTitle(), "Products");
    }

    @Description("Verify adding a product updates the cart badge count")
    @Test(groups = {"smoke"})
    public void cartBadgeUpdatesOnAddProduct() {
        // Arrange
        assertEquals(productsPage.getCartItemCount(), 0, "Cart should be empty initially");

        // Act
        productsPage.addProductToCart(Products.BACKPACK.id());

        // Assert
        assertEquals(productsPage.getCartItemCount(), 1, "Cart should have 1 item");
    }
}
