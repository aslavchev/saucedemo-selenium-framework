package com.saucedemo.testdata;

import com.saucedemo.config.TestConfig;

/**
 * Test user credentials for SauceDemo application.
 * Each user type exhibits different behavior in the application.
 *
 * @see <a href="https://www.saucedemo.com">SauceDemo</a>
 */
public enum TestUsers {

    /**
     * Standard user with full functionality.
     * Use for happy path testing.
     */
    STANDARD("standard_user"),

    /**
     * User that is locked out and cannot log in.
     * Use for testing authentication error handling.
     */
    LOCKED_OUT("locked_out_user"),

    /**
     * User that experiences UI issues (broken images, wrong product info).
     * Use for testing graceful handling of UI defects.
     */
    PROBLEM("problem_user"),

    /**
     * User that triggers various error states.
     * Use for testing error handling throughout the application.
     */
    ERROR("error_user"),

    /**
     * User that experiences performance issues (slow responses).
     * Use for testing timeout handling and loading states.
     */
    PERFORMANCE_GLITCH("performance_glitch_user"),

    /**
     * Invalid user credentials for negative testing.
     */
    INVALID("invalid_user", "invalid_password"),

    /**
     * Empty credentials for validation testing.
     */
    EMPTY("", "");

    private final String username;
    private final String customPassword;

    /**
     * Constructor for SauceDemo users (shared password from config).
     */
    TestUsers(String username) {
        this.username = username;
        this.customPassword = null;
    }

    /**
     * Constructor for custom credentials (e.g., invalid/empty for negative tests).
     */
    TestUsers(String username, String customPassword) {
        this.username = username;
        this.customPassword = customPassword;
    }

    public String username() {
        return username;
    }

    /**
     * Returns password at runtime (not class-load time).
     * SauceDemo users share the same password from config.
     */
    public String password() {
        return customPassword != null ? customPassword : TestConfig.getPassword();
    }

    /**
     * Get user credentials as formatted string (for logging).
     * Password is masked for security.
     */
    @Override
    public String toString() {
        return String.format("TestUser[%s]", name());
    }
}
