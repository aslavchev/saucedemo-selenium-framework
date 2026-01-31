package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.testdata.TestUsers;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Login functionality tests.
 */
public class LoginTest extends BaseTest {

    @Test(groups = {"smoke"})
    public void validUserCanLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestUsers.STANDARD.username(), TestUsers.STANDARD.password());

        assertTrue(loginPage.getCurrentUrl().contains("inventory"), "Should redirect to inventory page");
    }

    @Test(groups = {"smoke"})
    public void lockedOutUserSeesError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestUsers.LOCKED_OUT.username(), TestUsers.LOCKED_OUT.password());

        assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        assertTrue(loginPage.getErrorMessage().contains("locked out"), "Error should mention locked out");
    }

    @Test(groups = {"regression"})
    public void invalidPasswordShowsError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestUsers.STANDARD.username(), "wrong_password");

        assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        assertTrue(loginPage.getErrorMessage().contains("do not match"), "Error should mention credentials mismatch");
    }
}
