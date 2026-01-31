package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.testdata.Products;
import com.saucedemo.testdata.TestUsers;
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

    @Test(groups = {"smoke"})
    public void productsPageLoadsAfterLogin() {
        assertTrue(productsPage.isLoaded(), "Products page should be loaded");
        assertEquals(productsPage.getPageTitle(), "Products");
    }

    @Test(groups = {"smoke"})
    public void canAddProductToCart() {
        assertEquals(productsPage.getCartItemCount(), 0, "Cart should be empty initially");

        productsPage.addProductToCart(Products.BACKPACK.id());

        assertEquals(productsPage.getCartItemCount(), 1, "Cart should have 1 item");
    }
}
