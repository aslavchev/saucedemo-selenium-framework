package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.testdata.Products;
import com.saucedemo.testdata.TestUsers;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Description("Verify products can be sorted by name A-Z")
    @Test(groups = {"regression"})
    public void sortProductsByNameAscending() {
        // Arrange
        List<String> originalNames = productsPage.getProductNames();

        // Act
        productsPage.sortByNameAscending();

        // Assert
        List<String> sortedNames = productsPage.getProductNames();
        List<String> expectedOrder = new ArrayList<>(originalNames);
        Collections.sort(expectedOrder);
        assertEquals(sortedNames, expectedOrder, "Products should be sorted A-Z");
    }

    @Description("Verify problem user sees broken product images (all identical)")
    @Test(groups = {"regression"})
    public void problemUserHasBrokenProductImages() {
        // Arrange - login as problem user (not standard user from @BeforeMethod)
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(TestUsers.PROBLEM.username(), TestUsers.PROBLEM.password());
        ProductsPage problemProductsPage = new ProductsPage(driver);

        // Act
        List<String> imageSources = problemProductsPage.getProductImageSources();

        // Assert - all 6 images have same broken src (instead of 6 unique images)
        String firstImage = imageSources.get(0);
        for (String src : imageSources) {
            assertEquals(src, firstImage, "All images should be identical (broken) for problem user");
        }
    }

    @Description("Verify error user cannot remove item from products page")
    @Test(groups = {"regression"})
    public void errorUserCannotRemoveItemFromProductsPage() {
        // Arrange - login as error user and add product
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(TestUsers.ERROR.username(), TestUsers.ERROR.password());
        ProductsPage errorProductsPage = new ProductsPage(driver);
        errorProductsPage.addProductToCart(Products.BACKPACK.id());
        assertEquals(errorProductsPage.getCartItemCount(), 1, "Product should be added");

        // Act - try to remove product from inventory page
        errorProductsPage.removeProductFromCart(Products.BACKPACK.id());

        // Assert - item still in cart (removal failed)
        assertEquals(errorProductsPage.getCartItemCount(), 1,
                "Error user should not be able to remove items from inventory page");
    }
}
