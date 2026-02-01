package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutCompletePage;
import com.saucedemo.pages.CheckoutInfoPage;
import com.saucedemo.pages.CheckoutOverviewPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.testdata.Products;
import com.saucedemo.testdata.TestCustomers;
import com.saucedemo.testdata.TestUsers;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Checkout flow tests.
 */
public class CheckoutTest extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutInfoPage checkoutInfoPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;

    @BeforeMethod
    public void loginAndAddProduct() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestUsers.STANDARD.username(), TestUsers.STANDARD.password());
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutInfoPage = new CheckoutInfoPage(driver);
        checkoutOverviewPage = new CheckoutOverviewPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);

        productsPage.addProductToCart(Products.BACKPACK.id());
        productsPage.goToCart();
        cartPage.proceedToCheckout();
    }

    @Description("Verify completing checkout shows confirmation message")
    @Test(groups = {"smoke"})
    public void checkoutShowsConfirmationMessage() {
        // Arrange
        checkoutInfoPage.fillCustomerInfo(
                TestCustomers.STANDARD.firstName(),
                TestCustomers.STANDARD.lastName(),
                TestCustomers.STANDARD.postalCode());

        // Act
        checkoutInfoPage.continueToOverview();
        assertTrue(checkoutOverviewPage.isLoaded(), "Overview page should be loaded");
        checkoutOverviewPage.finishCheckout();

        // Assert
        assertTrue(checkoutCompletePage.isLoaded(), "Checkout should be complete");
        assertEquals(checkoutCompletePage.getHeader(), "Thank you for your order!");
    }

    @Description("Verify checkout fails when first name is missing")
    @Test(groups = {"regression"})
    public void checkoutFailsWithEmptyFirstName() {
        // Arrange
        checkoutInfoPage.fillCustomerInfo(
                "",
                TestCustomers.STANDARD.lastName(),
                TestCustomers.STANDARD.postalCode());

        // Act
        checkoutInfoPage.continueToOverview();

        // Assert
        assertTrue(checkoutInfoPage.isErrorDisplayed(), "Error should be displayed");
        assertTrue(checkoutInfoPage.getErrorMessage().contains("First Name is required"),
                "Error should indicate First Name is required");
    }

    @Description("Verify checkout fails when last name is missing")
    @Test(groups = {"regression"})
    public void checkoutFailsWithEmptyLastName() {
        // Arrange
        checkoutInfoPage.fillCustomerInfo(
                TestCustomers.STANDARD.firstName(),
                "",
                TestCustomers.STANDARD.postalCode());

        // Act
        checkoutInfoPage.continueToOverview();

        // Assert
        assertTrue(checkoutInfoPage.isErrorDisplayed(), "Error should be displayed");
        assertTrue(checkoutInfoPage.getErrorMessage().contains("Last Name is required"),
                "Error should indicate Last Name is required");
    }

    @Description("Verify checkout fails when postal code is missing")
    @Test(groups = {"regression"})
    public void checkoutFailsWithEmptyPostalCode() {
        // Arrange
        checkoutInfoPage.fillCustomerInfo(
                TestCustomers.STANDARD.firstName(),
                TestCustomers.STANDARD.lastName(),
                "");

        // Act
        checkoutInfoPage.continueToOverview();

        // Assert
        assertTrue(checkoutInfoPage.isErrorDisplayed(), "Error should be displayed");
        assertTrue(checkoutInfoPage.getErrorMessage().contains("Postal Code is required"),
                "Error should indicate Postal Code is required");
    }
}
