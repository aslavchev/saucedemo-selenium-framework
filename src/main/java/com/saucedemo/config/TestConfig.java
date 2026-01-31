package com.saucedemo.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralized configuration management.
 * Reads from environment variables with .env file fallback for local development.
 *
 * Priority: System Environment Variables > .env file > Default values
 */
public final class TestConfig {

    private static final Logger logger = LoggerFactory.getLogger(TestConfig.class);
    private static final Dotenv dotenv;

    static {
        dotenv = Dotenv.configure()
                .ignoreIfMissing()  // Don't fail if .env doesn't exist (CI environment)
                .load();
        logger.info("Configuration loaded successfully");
    }

    private TestConfig() {
        // Prevent instantiation
    }

    // ==================== Credentials ====================

    public static String getUsername() {
        return getEnv("SAUCE_USERNAME", "standard_user");
    }

    public static String getPassword() {
        return getEnv("SAUCE_PASSWORD", "secret_sauce");
    }

    // ==================== URLs ====================

    public static String getBaseUrl() {
        String url = getEnv("BASE_URL", "https://www.saucedemo.com");
        // Normalize: remove trailing slash
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    // ==================== Browser Configuration ====================

    public static String getBrowser() {
        return getEnv("BROWSER", "chrome").toLowerCase();
    }

    /**
     * Check if browser should run headless.
     * Priority: Maven property (-Dheadless=true) > environment variable > false
     */
    public static boolean isHeadless() {
        String headlessProperty = System.getProperty("headless");
        if (headlessProperty != null) {
            return Boolean.parseBoolean(headlessProperty);
        }
        return Boolean.parseBoolean(getEnv("HEADLESS", "false"));
    }

    // ==================== Paths ====================

    public static String getScreenshotPath() {
        return getEnv("SCREENSHOT_PATH", "target/screenshots");
    }

    // ==================== Helper Methods ====================

    /**
     * Get environment variable with fallback to .env file, then default value.
     *
     * @param key          Environment variable name
     * @param defaultValue Default value if not found
     * @return The configuration value
     */
    private static String getEnv(String key, String defaultValue) {
        // First check system environment (for CI)
        String systemValue = System.getenv(key);
        if (systemValue != null && !systemValue.isEmpty()) {
            return systemValue;
        }

        // Then check .env file (for local development)
        String dotenvValue = dotenv.get(key);
        if (dotenvValue != null && !dotenvValue.isEmpty()) {
            return dotenvValue;
        }

        // Fall back to default
        logger.debug("Using default value for {}: {}", key, defaultValue);
        return defaultValue;
    }

    /**
     * Log current configuration (for debugging).
     * Masks sensitive values.
     */
    public static void logConfiguration() {
        logger.info("=== Test Configuration ===");
        logger.info("Base URL: {}", getBaseUrl());
        logger.info("Browser: {}", getBrowser());
        logger.info("Headless: {}", isHeadless());
        logger.info("Username: {}", getUsername());
        logger.info("Password: ****");
        logger.info("==========================");
    }
}
