package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.testdata.TestUsers;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Login functionality tests.
 */
public class LoginTest extends BaseTest {

    @Description("Verify standard user can login and reach inventory page")
    @Test(groups = {"smoke"})
    public void loginSucceedsWithStandardUser() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver);

        // Act
        loginPage.login(TestUsers.STANDARD.username(), TestUsers.STANDARD.password());

        // Assert
        assertTrue(loginPage.getCurrentUrl().contains("inventory"), "Should redirect to inventory page");
    }

    @Description("Verify locked out user sees appropriate error message")
    @Test(groups = {"smoke"})
    public void loginFailsForLockedOutUser() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver);

        // Act
        loginPage.login(TestUsers.LOCKED_OUT.username(), TestUsers.LOCKED_OUT.password());

        // Assert
        assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"),
                "Error should indicate user is locked out");
    }

    @Description("Verify invalid password shows credentials mismatch error")
    @Test(groups = {"regression"})
    public void loginFailsWithInvalidPassword() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver);

        // Act
        loginPage.login(TestUsers.STANDARD.username(), "wrong_password");

        // Assert
        assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error should indicate credentials mismatch");
    }
}
